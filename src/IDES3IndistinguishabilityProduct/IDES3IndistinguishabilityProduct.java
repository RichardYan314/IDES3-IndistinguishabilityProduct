package IDES3IndistinguishabilityProduct;

import ides.api.plugin.Plugin;
import ides.api.plugin.PluginInitException;
import ides.api.plugin.operation.OperationManager;

public class IDES3IndistinguishabilityProduct implements Plugin {

    public String getCredits() {
        return "Richard Ean";
    }
    public String getDescription() {
        return "IDES 3 Indistinguishability Product Plugin\nD. Sears and K. Rudie, \"On computing indistinguishable states of nondeterministic finite automata with partially observable transitions,\" 53rd IEEE Conference on Decision and Control, 2014, pp. 6731-6736, doi: 10.1109/CDC.2014.7040446.";
    }
    public String getLicense() {
        return "";
    }
    public String getName() {
        return "IDES 3 Indistinguishability Product Plugin";
    }
    public String getVersion() {
        return "0.1";
    }
    public void initialize() throws PluginInitException {
        OperationManager.instance().register(new IndistinguishabilityProduct());
    }
    public void unload() {
    }
}