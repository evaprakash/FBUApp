# *App Title Pending*

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
The goal of this app is to connect domestic violence/SA victims with each other and the relevant resources based on their preferences.

### App Evaluation

- **Category:** Social Networking
- **Mobile:** Potentially can be used on computer, but prioritizes mobile experience. 
- **Story:** Allow users to make posts and interact with other users to share experiences and information. Additionally, connects users with the best resources based on their preferences.
- **Market:** Victims of domestic violence/SA in the U.S.
- **Habit:** Users can use this app at any time - very habit forming.
- **Scope:** For feasibility, this would cater only to individuals in the U.S.

## Product Spec

### User Stories (Required and Optional)

**Required Must-have Stories**

* Login Screen
* User can login
* Registration Screen
* User can create a new account
* User can view a feed
* User can post to their feed
* User can like/comment on other posts
   * Double tap to like
   * Animate heart over post when liking
* Difficult/ambiguous technical problem (finding "best" resources for user based on their needs)
   * User can enter preferences in a number of different categories (i.e. resource type, location radius, price, rating, hours)
   * Find and rank filtered resources based on their preferences
   * Display resource details when tapped

**Optional Nice-to-have Stories**

* Options for different languages
* Password protected feed
* Uploading profile pictures (?)
* Following/friending people (?)
* Messaging component (?)

### Screen Archetypes

* Login / Register
    * User signs up or logs into their account 
* Stream
   * Viewing feed
* Creation
    * User can create new post
    * User can like/comment on other posts
* Profile
    * User can view their identity and stats
* Map View
    * Link to resources
    * View results page after entering resources

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home Feed
* Like/Comment
* Post a Photo
* Profile
* Resource search
* Logout

**Flow Navigation** (Screen to Screen)

* Login Screen
    * Home
    * Registration Screen
* Stream Screen
    * Scroll through feed, like posts
* Creation screen
    * View post details, comment
* Profile Screen
    * View user details
* Map Screen
    * Filter resources by preferences
    * Results page
* Logout Button

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="https://github.com/evaprakash/FBUApp/blob/master/FBUApp.png" width=600>

### Milestones

* Week 1: Build out the skeleton of app
   * [X] Log in/sign up/logout
   * [X] Fragments for make post (no picture required), home feed, personal profile, search 
   * [X] Enable likes on posts (double tapping, animation)
* Week 2: Build out core features, with a focus on your difficult/ambiguous technical problem(s)
   * Fix likes persistency / add comments
   * Personal profile grid view
   * Enable user to input preferences in given categories
   * Use Yelp API to extract relevant information in categories
* Week 3: Complete remaining core features, Start on complex algorithm part
   * Determine ranking system for best correlating preferences with Yelp categories
* Week 3: Complete Complex algorithm part and wrap up any remaining features
   * Display rankings and resource details (e.g. images of resource provided by Yelp, etc.)
* Week 4: Polish / Stretch goals
   * Password-protected feed
   * UI fixes in feed/resource finding

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
