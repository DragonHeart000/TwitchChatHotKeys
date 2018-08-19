# Welcome

Thank you for wanting to contribute to Twitch Chat Hot Keys! All help is greatly appreciated! Please read this file before contributing and make sure that you follow the guidelines!

# Getting started

Before you start please ensure that you have the most recent version and that whatever you're trying to contribute is not already finished but just not in a published release. Lots of time code will be added but there will not be a compiled version added to the releases.

Once you have done that make sure you communicate what you plan on doing; this is best done via an issue but can be done by personally contacting me.

# Where to start?

If you're just looking to help out the best way to start would be by browsing the [issues tab](https://github.com/DragonHeart000/TwitchChatHotKeys/issues) to find something that needs to be worked on or by using the software, finding something you feel is lacking, and submitting an issue with it.

## Priority 

With contributing it is not necessary for you to contribute something that is a high priority, contribute whatever you feel you want to work on. That being said there is still a way that issues are prioritized.

Priority is assigned as such (highest to lowest)

1. `TODO: Next update` These are things that have either a large amount of work done already and will be released soon or things that can not wait to be patched.
2. `TODO: Soon` These are things that have some to no work done but are high up on the list of things to do.
3. `TODO: Eventually` These are things that are planned and are important but not needed soon and other things must be done first.
4. `Idea` Lastly these are things that may not even be added but may be considered.

# Guidelines For Code

## Errors

There is a class in the program called ErrorHandleing. Whenever handling an error makes sure that at some point this is called to warn the user if the error is vital.

There are two methods in the class, `error(Exception e, String context)` and `error(Exception e)` both of these bring up a dialog with the error, however, the prior allows for a custom message to be appended on.

## Commenting

Please make sure to comment your code and that your comments follow the general guide of how comments are done.

Large sections of code that all portray to eachother should be under a comment that titles the section. This looks like this:
`/////////////////////////////////////Title of section///////////////////////////////////////`
These sections may have anywhere from just one method to a large sum of methods but all code under a section must make sense to go together. (See the FileHandleing class for a good example of this)

I have been trying very hard to keep this code well documented and commented so please try to continue the efforts. If a section is found that makes no sense to you feel free to submit an issue for it.
