### [My IDES3 Plugins](https://github.com/RichardYan314/IDES3-Plugins)

# IDES3 Indistinguishability Product

A [IDES3](https://github.com/krudie/IDES) plugin
to compute indistinguishability products.

D. Sears and K. Rudie,
"On computing indistinguishable states of nondeterministic finite automata with partially observable transitions,"
*53rd IEEE Conference on Decision and Control*,
2014, pp. 6731-6736, doi: [10.1109/CDC.2014.7040446](https://doi.org/10.1109/CDC.2014.7040446).

# Getting Started

## Prerequisites

Current version (v1.0.0) requires:

- JRE: v17
- [IDES3](https://github.com/krudie/IDES): v3.1.3

This plugin requires JRE v17.

The plugin is developed for IDES3 v3.1.3.
It is not known at all whether the plugin works with IDES3 of a higher or lower version.

## Installing

Assuming you have the required tool, otherwise see

- [IDES3 installation](https://github.com/krudie/IDES#installation)

Assuming `./` refers to the root path of your IDES3 installation,
i.e., where `IDES-[VERSION].jar` is located.

Download the file [`IDES3IndistinguishabilityProd.IDES3IndistinguishabilityProd.jar` (link)](https://github.com/RichardYan314/IDES3-IndistinguishabilityProduct/releases)
and place it under `./plugins`.

## Usage

Launch IDES3!

From the toolbar, click through `Operations -> DES Operations`,
the operation should be present as `indist_prod`.

## Note

The indistinguishability product
is usually invoked over a single automaton,
i.e., \(G \otimes G\).
In this case setting the argument `symmetry` to `True`
combines symmetric states in the output.
If the input automata are not the same,
the option `symmetry` will be ignored with a warning message prompted.
The input automata are considered the same
only when they are the same model;
two identical models are not considered the same.

*The operation currently assumes input automata
contain no cycles of unobservable transitions.*

## Versioning

The project is first published under the version v1.0.0
as I consider it fully functioning.
As improvements being made, I will keep incrementing the MINOR and PATCH verion,
depending on the actual changes.

## Authors

* [**Richard Yan**](https://github.com/RichardYan314)

# License

This project is licensed under the GNU Affero General Public License license v3.0,
following the licensing of IDES3api.

See the [LICENSE.md](LICENSE.md) file for the full text.