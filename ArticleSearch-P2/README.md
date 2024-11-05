# Lab 5: ArticleSearch Pt 2

Course Link: [CodePath Android Course](https://courses.codepath.org/courses/and102/unit/5#!labs)

Submitted by: **Raphael Joseph Holganza** <!-- Replace 'Your Name Here' with your actual name -->

**NYT Article Search Pt 2** is an app designed to maintain functionality while offline by caching the latest data fetched from the NYT API, ensuring a smooth user experience even without network connectivity.

Time spent: **4** hours spent in total <!-- Replace 'X' with the number of hours you spent on this project -->

## Application Features

### Required Features

The following **required** functionality is completed:

- [x] (2 pts) **Most recently fetched data is stored locally in a database**
  - The app should cache the latest articles fetched from the NYT API in a local SQLite database using Room.
  - If the user has fetched data recently, those articles should be available offline.
  - Ensure old cached data is properly replaced with new data upon successful network fetches.
  -  ![offlineGif](https://github.com/user-attachments/assets/ed437a23-bf08-4ab3-9d7e-367df31de790) <!-- Replace this link with your actual image/GIF link -->

- [x] (2 pts) **If user turns on airplane mode and closes and reopens app, old data from the database should be loaded**
  -  ![airplanemodeGif](https://github.com/user-attachments/assets/73a58bc6-3520-42eb-849b-30515f68490a) <!-- Replace this link with your actual image/GIF link -->

### Stretch Features

The following **stretch** functionality is implemented:

- [x] (2 pts) **Add Swipe To Refresh to force a new network call to get new data**
  - ![swiperefreshGif](https://github.com/user-attachments/assets/5bb88ad0-b5dc-4ea1-8dcc-3a17a08815dc) <!-- Replace this link with your actual image/GIF link -->

- [x] (2 pts) **Add setting toggle for user to create preference for caching data or not (Using Shared Preferences)**
  -  ![cachingdataGif](https://github.com/user-attachments/assets/a96ffe07-4609-4a85-8995-bd5264ae8f9a)<!-- Replace this link with your actual image/GIF link -->

- [x] (+3 pts) **Implement a Search UI to filter current RecyclerView entries or fetch data from the search API with query**
  -  ![uisearchGif](https://github.com/user-attachments/assets/2cf602c1-5f3c-43fc-915c-9728444df396) <!-- Replace this link with your actual image/GIF link -->

- [x] (2 pts) **Listen to network connectivity changes and create a UI to let people know they are offline and automatically reload new data if connectivity returns**
  -  <![swiperefreshGif](https://github.com/user-attachments/assets/58f1cc95-b239-433f-a7b8-f1201399e6be) <!-- Replace this link with your actual image/GIF link -->

## Notes

Issues with the UI search and adding the refresh feature when swiping the app.
Also trying to have the UI show up for the user depending on the network connection also had some difficulties. <!-- Replace this with your specific challenges and experiences -->

## Resources

- [Data storage with Room](https://developer.android.com/training/data-storage/room)
- [Swipe To Refresh](https://developer.android.com/training/swipe/add-swipe-interface)
- [Save key-value data with Shared Preferences](https://developer.android.com/training/data-storage/shared-preferences)
- [Android Search View](https://developer.android.com/reference/android/widget/SearchView)
- [Monitor connectivity status and connection metering](https://developer.android.com/training/monitoring-device-state/connectivity-status-type)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

## License

```plaintext
    Copyright [2024] [Raphael Joseph Holganza]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
