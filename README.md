# TwitchChatHotKeys
TwitchChatHotKeys is a program that allows Twitch Streamers to bind any key on their keybaord to say anything in twitch chat. Most common uses would be for running a commercial, moderating chat with timeouts/bans, or putting chat in followers/subscribers only mode, but anything can be done with it.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Java 9 or later is required to run Twitch Chat Hot Keys due to the JavaFX library.

## Deployment

Given that you have Java 9 or newer installed you are able to just run the jar file to execute TwitchChatHotKeys and start.

If you downloaded the source you are able to compile the project into a jar file using your preferred compiler. Be sure to have Maven for the dependencies.

All saves are currently saved in C://TwitchChatHotKeys/ However, this may be changed in the future and likely will be at least for certain Operating Systems.

## Setup

The first time you launch TwitchChatHotKeys you will be greeted with a welcome screen, this has a future plan of telling users when there are updates but currently is just to welcome first time users.

After continuing from there from then on you will go straight to the login screen:

![Login screenshot](/Screenshots/sctl.png?raw=true)

Provide your twitch.tv username and oauth or if you saved it last time go to file>load saved settings (You must do this to load any hotkeys you had saved in the past). After loading you will have this main screen:

![Main screenshot](/Screenshots/sctm.png?raw=true)

The UI is temporary and will be replaced with a better one in the future. Here you can do three new things

### Add new binds

You add a new bind by hitting the "Click to set bind" button, hitting a key that you want the bind to be set to, typing in the output of the bind, and then hitting save.

### Check binds

To check a bind you can hit the "Click to set bind" button, hitting a key, and then the output of the bind will be shown in the OutPut of bind box.

### Delete bind

To delete a bind you can hit the "Click to set bind" button, hit a key, confirm that you picked the correct one by reading the output of the bind in the OutPut box, and then confirm it by hitting "Confirm"

### Skinning

To skin your program you can go to `file > Change Skin` and select from some premade skins, link your own file, or create a custom one (coming soon).

## Built With

* [PircBotX](https://github.com/TheLQ/pircbotx) - IRC Library
* [jnativehook](https://github.com/kwhat/jnativehook) - Key Board Listener
* [org.json](https://mvnrepository.com/artifact/org.json/json) - JSON file reading for APIs
* [Maven](https://maven.apache.org/) - Dependency Management
* [JavaFX Themes](https://github.com/joffrey-bion/javafx-themes) - Temp skin (Dark Modena)

## Contributing

Please read [CONTRIBUTING.md](https://github.com/DragonHeart000/TwitchChatHotKeys/blob/master/CONTRIBUTING.md) for details on the process for helping with Twitch Chat Hot Keys.

## Versioning

MAJOR.MINOR.PATCH

MAJOR version when you make incompatible API changes,
MINOR version when you add functionality in a backwards-compatible manner
PATCH version when you make backwards-compatible bug fixes

## Authors

* **Riley Shumway** - *Initial work, updates, and managment* - [WebSite](https://dragonheart.ninja/)

See also the list of [contributors](https://github.com/DragonHeart000/TwitchChatHotKeys/graphs/contributors) who participated in this project.

## License

This project is licensed under the GNU General Public License - see the [LICENSE.md](https://github.com/DragonHeart000/TwitchChatHotKeys/blob/master/LICENSE) file for details
