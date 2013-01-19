HTTP Stress Tester
--

Stress tests HTTP servers by sending a large number of requests per second, using threads. Works best when used by multiple people targeting a single server.


Current Version
--

1.2.14
+ Added javadoc comments
+ Added method to gracefully terminate the program
+ Added thread counting class to call program terminate method once all threads are complete
* Modified thread starting logic to be more efficient
* Made cleaner system for asking user for input
* Split main method up into smaller methods
* Refactored code to be more efficient
* Changed how errors are handled slightly


License Info
--

Copyright (C) 2012  Tom Penney

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

See <http://www.gnu.org/licenses/gpl-3.0.html> for the full GNU General Public License.
