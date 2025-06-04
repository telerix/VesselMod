# Vessel Mod

Slay the Spire modded character of the Knight (Vessel) from Hollow Knight.

All custom art, SFX and other assets belong to Team Cherry, the developers of Hollow Knight.

Character Design inspired by The Bug Knight mod by VenIM.

Translations credit:
- Chinese (Simplified): @Timale

---
## Changelogs / WIPs

### WIPs
- Small rework on Radiance Implosion, Empower and Soul Barrage [ver 0.5.0]
- General damage scaling (Multiapply Infection cards) [ver 0.5.0]
- Higher block increase for soul-cost block cards [ver 0.5.0]
- More sources of energy generation [ver 0.5.0]
- Possibly more common Ethereal synergy cards
- Add custom SFX for cards

### Version 0.5.0 (Current)
- You can now gain Soul on fully blocked attack damage
- Acid Armor: 12(16) -> 13(17) Block
- Conjure Shield: 6(8) -> 6(9) Block
- Soul Shield 3(4) -> 4(5) Block
- Well Prepared 5(8) -> 6(9) Block
- No Mind To Think: 2(3) -> 3(4) Block
- Forgotten Light: 5(7) Infection -> 2(3) Infection twice
- Radiance Implosion: Rework to catalyst clone - 1 energy. Deals AOE damage equal to 2(3) times the target's Infection
- Empower, Spirit Barrage: Now uses "X" for Soul cost, with their effects amplified by Chemical X
- Empower: 2 Str + {2 -> 1 Dex} per {3 -> 2 Soul}
- Corrupted Nail: Ethereal, 1(2) Infection -> )Ethereal(, 1 Infection
- Infection Outbreak: 6 -> 8 self Infection
- Channeled Beam: Apply lost Infection to all enemies -> apply 1(2) Infection 3 times
- Light Orb: Removed
- Energizing Torrent (New uncommon skill): Unplayable. When you use Soul, discard this card and gain 1(2) Energy


- Fixed drawn Ethereal cards exhausting immediately from Void Embrace + Ethereal Burns at the end of the turn
- Fixed Quickcast power description not updating after each use
- Cards now glow briefly when Ethereal is added to them


### Version 0.4.2
- Added Simplified Chinese language strings (Credit: @Timale)

### Version 0.4.1
- Fixed Soul Boomerang upgrade
- Fixed Radiant Implosion card description on card hover
- Fixed Void Form not showing 0 Soul cost on cards
- Fixed red upgrade text on Soul Coagulation and Deep Focus (Temporary)
- Fixed Soul Jar getting Ethereal (Retain cards always override Ethereal.)

---
## Guidelines for Translations
Thank you very much for considering providing translations to the mod! If you would like to provide translations to a currently-unsupported language, please follow the following guidelines:

- Add me (tlix) on Discord and send me a DM. You can easily find me in the official StS Discord server, under the dedicated post in #modding-forum. 
  - The Discord server invite link: https://discord.com/invite/slaythespire
- Fork the latest branch of the repository **(not main)**, copy the folder at `resources/vesselmod/localization/eng` and rename the copy to `resources/vesselmod/localization/<your_language>`. Alternatively, ask me for the `eng` folder if you don't use GitHub.
- The `.json` files in the folder contains all the strings for the entire mod. In each of the files, ONLY change the items under `"NAME"`, `"DESCRIPTION"`, and any similar fields.
  - Prefix all the custom keywords used in card strings with `${modID}:`, e.g. `${modID}:<Infection_in_your_language>`. For base game keywords (e.g. Ethereal, Exhaust), I'm pretty sure you can leave them alone.
  - Some entries may not be obvious to fill in due to how the text is pieced together in-game. You can check the in-game English text to see where the numbers go, but do ask me if you are unsure about it.
  - You don't need to translate the default/template (usually the last entry in the file), or any items with `[DEPRECIATED]` in the name field.
  - **Do not change the order, or delete any of the entries.** This is to check for any possible missing strings that will likely cause a game crash.
  - That being said, I might add new entries to the .json files myself in content updates. In that case, I will always give you the exact lines for you to insert into your files.
- To ensure translation quality and consistency, your translations should follow these rules:
  1. If the name/phrase exists in Hollow Knight, use the exact word/phrase from the original game.
  2. Follow the phrasing from the base game if possible. For example, `Infection` is pieced together from Silent's `Poison` and Watcher's `Pressure Points`.
  3. There shouldn't be more than one way to interpret your line, e.g. the clear difference between `Take damage` and `Lose HP`. Since I won't be able to check it if I don't know the language, it's up to your judgement.
  4. Try to keep it as concise as possible.
- When you are done, submit a pull request on GitHub, or send me the folder through Discord.
  - If you don't tell me on Discord about this, the pull request will not be accepted. 
- After checking for any missing entries, I will compile the mod and also send a copy to you for testing purposes. If there are no issues or any broken text, it will be released as is. Thank you!
- If new lines of translations are required, I will inform you after the content is finalized through Discord, so please check your DMs often.