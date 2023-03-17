# PackMode

PackMode is a simple, config based mod that allows Pack Developers to
enable and disable recipe tweaks and features depending on the selected PackMode.

This is useful to ship two versions of a pack within one, or a more complex version of it, still with the same mods and
registered items and blocks, but with dynamic recipes.

## Changing the PackMode

The PackMode can be changed using the Config or the /packmode command in game.

## Compat

By itself, PackMode has compat with Vanilla and CraftTweaker:

### Forge

For a Forge conditioned datapack, you'll need the following condition, with the list of required packmodes for this to load.
The recipe can be any type of recipe that loads regularly with a datapack.

```json
{
  "type": "forge:conditional",
  "recipes": [
    {
      "conditions": [
        {
          "packModes": [
            "expert"
          ],
          "type": "packmode:active"
        }
      ],
      "recipe": {
        "type": "minecraft:crafting_shaped"
        ... //The rest of the recipe goes here, shortened for commodity.
      }
    }
  ]
}
```

### Fabric

```json
{
  "fabric:load_conditions": [ 
    {
    "condition": "packmode:active",
      "value" : {
        "packModes" : [
          "expert"
        ]
      }
    }
  ],
  "type": "minecraft:crafting_shaped",
    ...//The rest goes here
}

```

### CraftTweaker

When installed with CraftTweaker, packmode introduces the `#packmode` preprocessor.

The preprocessor is appended at the top of a script, and is followed by a list of variable length of packmodes.
If any of the packmodes provided matches the current packmode, that script is loaded.


