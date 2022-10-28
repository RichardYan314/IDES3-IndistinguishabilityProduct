package IDES3IndistinguishabilityProduct

import java.util

import ides.api.core.Hub
import ides.api.model.fsa.FSAModel
import ides.api.model.fsa.FSAState
import ides.api.plugin.model.ModelManager
import ides.api.plugin.operation.Operation

import scala.jdk.CollectionConverters._
import scala.collection.mutable

class IndistinguishabilityProduct extends Operation {
  override def getName: String = "indist_prod"

  override def getDescription: String = """
    |Construct indistinguishability product of two automata.
    |If `symmetric` is set to true and the two input automata are equal,
    |the output will be optimized to combine symmetric states
  """

  override def getNumberOfInputs: Int = 3

  override def getTypeOfInputs: Array[Class[_]] = Array(
    classOf[FSAModel], classOf[FSAModel],
    classOf[java.lang.Boolean]) // cannot use scala Boolean as it is equivalent to java's primitive boolean

  override def getDescriptionOfInputs: Array[String] = Array("G1", "G2", "symmetric")

  override def getNumberOfOutputs: Int = 1

  override def getTypeOfOutputs: Array[Class[_]] = Array(classOf[FSAModel])

  override def getDescriptionOfOutputs: Array[String] = Array("indist_prod")

  private var map: mutable.Map[(FSAState,FSAState), FSAState] = null
  private var pending: mutable.Queue[((FSAState, FSAState), FSAState)] = null
  private var X: FSAModel = null
  private var symm: Boolean = false

  override def perform(args: Array[AnyRef]): Array[AnyRef] = {
    warnings.clear()

    val G1 = args(0).asInstanceOf[FSAModel]
    val G2 = args(1).asInstanceOf[FSAModel]
    symm = args(2).asInstanceOf[Boolean]

    val X01 = G1.getStateIterator.asScala
      .filter(_.isInitial)
      .toList
    if (X01.size != 1) {
      warnings.add("G1 must have exactly 1 initial state")
      return Array.empty
    }
    val x01 = X01.head

    val X02 = G2.getStateIterator.asScala
      .filter(_.isInitial)
      .toList
    if (X02.size != 1) {
      warnings.add("G2 must have exactly 1 initial state")
      return Array.empty
    }
    val x02 = X02.head

    if (symm && G1 != G2) {
      warnings.add("Input automata are not equal. Option `symmetric` is ignored")
      symm = false
    }

    map = mutable.Map()
    pending = mutable.Queue()

    X = ModelManager.instance().createModel(classOf[FSAModel])

    buildState(x01, x02).setInitial(true)
    try {
      while (pending.nonEmpty) {
        val ((x, y), xy) = pending.dequeue()

        G2.getTransitionIterator.asScala
          .filter(_.getSource eq y)
          .filter(_.isEpsilonTransition)
          .map(_.getTarget)
          .foreach(y2 => {
            X.add(X.assembleEpsilonTransition(xy.getId, buildState(x, y2).getId))
          })

        G1.getTransitionIterator.asScala
          .filter(_.getSource eq x)
          .filter(_.isEpsilonTransition)
          .map(_.getTarget)
          .foreach(x2 => {
            X.add(X.assembleEpsilonTransition(xy.getId, buildState(x2, y).getId))
          })

        G1.getTransitionIterator.asScala
          .filter(_.getSource eq x)
          .filter(!_.isEpsilonTransition)
          .foreach(t => {
            val sigma = t.getEvent
            val x2 = t.getTarget
            G2.getTransitionIterator.asScala
              .filter(_.getSource eq y)
              .filter(!_.isEpsilonTransition)
              .filter(_.getEvent eq sigma)
              .map(_.getTarget)
              .foreach(y2 => {
                if (!X.getEventSet.contains(sigma)) X.add(sigma)
                X.add(X.assembleTransition(xy.getId, buildState(x2, y2).getId, sigma.getId))
              })
          })
      }

      X.setName("G1 X G2")
      Hub.getWorkspace.addModel(X)

      return Array(X)
    } catch {
      case e: Exception => {
        e.printStackTrace()
      }
    }
    return Array.empty
  }

  private def buildState (x: FSAState, y: FSAState): FSAState = {
    val pair = (x, y)
    val riap = (y, x)
    if (map.contains(pair)) {
      return map(pair)
    } else if (symm && map.contains(riap)) {
      return map(riap)
    } else {
      val xy = X.assembleState()
      xy.setName(s"(${x.getName}, ${y.getName})")
      xy.setAnnotation("src", pair)

      pending.enqueue((pair, xy))
      X.add(xy)
      map.put(pair, xy)
      return xy
    }
  }

  private val warnings: util.List[String] = new util.LinkedList()

  override def getWarnings: util.List[String] = warnings
}