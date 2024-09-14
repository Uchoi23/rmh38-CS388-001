# Lab 1: Tap Counter App

Course Link: [CodePath Android Course](https://courses.codepath.org/courses/and102/unit/1#!labs)

Submitted by: **Raphael Joseph Holganza** <!-- Replace 'Your Name Here' with your actual name -->

**Tap Counter App** is an Android app inspired by idle tap games, allowing users to accumulate points through taps, which can be exchanged for upgrades.

Time spent: **4** hours spent in total <!-- Replace 'X' with the number of hours you spent on this project -->

## Application Features

### Required Features

The following **required** functionality is completed:

- [x] (2 pts) **User can see a number displayed on the screen. The number starts at 0.**  
      ![Tapped0](https://github.com/user-attachments/assets/6bc63892-8543-4f43-8302-accf47ad2fb6)


- [x] (2 pts) **User can tap on a button to see the number displayed increase by 1.**
      ![showingTapButton](https://github.com/user-attachments/assets/bec103fd-0acc-448f-843c-95826a70b0e6)


### Stretch Features

The following **stretch** functionality is implemented:

- [x] (2 pts) **User can exchange the number of taps accumulated for upgrades:**
  - X taps for an upgrade that makes each tap count as 2 taps. (Default: 100 taps)
    - Use a Toast for positive (purchase) or negative (can't afford) notifications.
    ![doubleTapUpgrade](https://github.com/user-attachments/assets/d2ef47ad-24e6-4e29-af58-9837863dc504)

    
- [x] (2 pts) **User can exchange taps for a custom icon button.**  
  - X taps for a custom icon button. (Default: 100 taps)
    - Use a Toast for positive (purchase) or negative (can't afford) notifications.
    ![changeTapButtonUpgrade](https://github.com/user-attachments/assets/dbed0c6e-bae3-46f8-ad42-34627b9273f5)

    
- [x] (2 pts) **User can customize the app's theme (e.g., a dog-themed background with a paw print button).**  
  ![Tapped0](https://github.com/user-attachments/assets/801ff6ff-e4ad-4879-9d80-065237a87f6f)


- [x] (+2 bonus pts) **User has progressively difficult goals to reach in terms of the number of taps accumulated.**  
  - Display each goal on the screen, and track the total number of goals reached.
  - Use a Toast to notify of a reached goal.
  - ![goalReachedFeature](https://github.com/user-attachments/assets/1a10aedd-1a0d-4bf0-859b-f0a0ef3f56c8)


## Notes
- App kept crashing because I was trying to refrence a button by findViewById but instead it was an image button.
- Change Button Upgrade caused the button to have an incorrect size due to the lack of scaling.
- The number of taps wouldnt correctly show up due to the placement for the code that incremented the tap counter.

## Resources

- [ConstraintLayout documentation](https://developer.android.com/training/constraint-layout)
- [Displaying Toasts](https://guides.codepath.com/android/Displaying-Toasts)

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
