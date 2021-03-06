# ASMR Contestant Table
Simple Android application that manages a contest for ASMR artists.

## Objective
This app manages a simple contest for ASMR artists. The user adds contestants and manages them by
updating their attributes based on his experience. It also has a "Week Highlights" section, where
the user can feature contestants that performed best in a particular week.

**Important:**
As it stands now, contestants cannot be excluded individually, therefore, if any mistake is made 
during insertion or while updating a contestant data, the user will only be able to correct their
mistake by clicking on the "Reset Contest" option in the menu. Rebooting the contest will delete 
all contestants and clear the week highlights section.

## Development Technologies
3rd party libraries used:

1. <a href="https://developer.android.com/training/data-storage/room">Room</a>;
2. <a href="https://developer.android.com/kotlin/coroutines">Kotlin Coroutines for Android</a>.

## License
```
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
