# Android Project 1 - *Simple Wordle*

Submitted by: **Raphael Joseph Holganza**

**Simple Wordle** is an android app that recreates a simple version of the popular word game [Wordle](https://www.nytimes.com/games/wordle/index.html). 

Time spent: **4** hours spent in total

## Required Features

The following **required** functionality is completed:

- [x] **User has 3 chances to guess a random 4 letter word**
- [x] **After 3 guesses, user should no longer be able to submit another guess**
- [x] **After each guess, user sees the "correctness" of the guess**
- [x] **After all guesses are taken, user can see the target word displayed**

The following **optional** features are implemented:

- [ ] User can toggle between different word lists
- [ ] User can see the 'correctness' of their guess through colors on the word 
- [ ] User sees a visual change after guessing the correct word
- [ ] User can tap a 'Reset' button to get a new word and clear previous guesses
- [ ] User will get an error message if they input an invalid guess
- [ ] User can see a 'streak' record of how many words they've guessed correctly.

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

- Guessing word correctly
- ![wordleGif1](https://github.com/user-attachments/assets/9c291397-7ebd-4b6c-a014-a8106453770a)

- Using up all attempts
- ![ezgif-2-14ec04bbea](https://github.com/user-attachments/assets/34267e3c-6fe0-43ea-9fed-2b0d35e797cc)

<!-- Replace this with whatever GIF tool you used! -->
GIF created with ezgif.com

## Notes

Describe any challenges encountered while building the app.

- When I was comparing the user's guess with the correct word, the app would count the guess wrong
  due to case sensitivity even though the guess was correct. Both strings were converted to uppercase
  to solve this issue.

## License

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
