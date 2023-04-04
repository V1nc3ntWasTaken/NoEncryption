# The current repository is no longer being updated, and will be removed in the future. Please change your sources to [here](https://github.com/Doclic/NoEncryption), which I am currently overseeing.

# NoEncryption
NoEncryption is a very simple plugin designed that removes the signature from player messages. This means that your players won't be chat reported!

## Disclaimer
This **is** a modified, remastered version of [Doclic's Original NoEncryption](https://github.com/Doclic/NoEncryption).

This remastered version adds features such as automatic compatibility downloader (For if you accidentally download an incompatible version), unsecured chat pop-up disabling, 1.19.1+ support, and some other small features.

<details><summary>Copyright</summary>

Technically, recreation and redistributing is allowed due to [Doclic/NoEncryption's license](https://github.com/Doclic/NoEncryption/blob/main/LICENSE), so read this before you hound me.

</details>

## Details

Since 1.19, chat messages are digitally signed by Microsoft/Mojang. This means that all of your messages have a code attached to them that proves that **you** sent a chat message. It's kinda like an IRL signature, just way more secure, and harder to forge. Remastered NoEncryption works to strip all chat messages of their signatures before other plugins, and clients have a chance to access, copy, or modify any chats. This is the feature that makes it compatible to nearly all chat plugins, or modifiers (Including DiscordSRV).

<b>I am currently working to determine ViaVersion compatibility, and BungeeCord connections.</b>

<b>Unlike some other NoEncryption type plugins, this plugin won't allow banned players to join.</b>

## Installation
Download the JAR that is supported by your Minecraft version from the [latest release](https://github.com/V1nc3ntWasTaken/NoEncryption/releases), and add it to your server's plugins directory. When updating the plugin, your players may need to disconnect for the changes to fully be applied. If you download an incorrect version, the plugin will attempt to download the correct version for your Minecraft version. 

## Usage
The plugin is always enabled unless otherwise stated in the console. If you receive a message for a compatibility error, restart your server once the plugin states that it is safe to do so (The server will need to require an internet connection, otherwise, you may need to manually install the correct version). If you encounter any unexpected errors, please [submit a bug report issue](https://github.com/V1nc3ntWasTaken/NoEncryption/issues) for me to review.
