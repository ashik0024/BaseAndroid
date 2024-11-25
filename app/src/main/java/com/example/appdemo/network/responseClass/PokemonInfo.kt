package com.example.appdemo.network.responseClass


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Serializable
@Parcelize
data class PokemonInfo(
    @SerialName("abilities")
    val abilities: List<Ability?>? = listOf(),
    @SerialName("base_experience")
    val baseExperience: Int? = 0,
    @SerialName("cries")
    val cries: Cries? = Cries(),
    @SerialName("forms")
    val forms: List<Form?>? = listOf(),
    @SerialName("game_indices")
    val gameIndices: List<GameIndice?>? = listOf(),
    @SerialName("height")
    val height: Int? = 0,
    @SerialName("held_items")
    val heldItems: List<HeldItem?>? = listOf(),
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("is_default")
    val isDefault: Boolean? = false,
    @SerialName("location_area_encounters")
    val locationAreaEncounters: String? = "",
    @SerialName("moves")
    val moves: List<Move?>? = listOf(),
    @SerialName("name")
    val name: String? = "",
    @SerialName("order")
    val order: Int? = 0,

    @SerialName("species")
    val species: Species? = Species(),
    @SerialName("sprites")
    val sprites: Sprites? = Sprites(),
    @SerialName("stats")
    val stats: List<Stat?>? = listOf(),
    @SerialName("types")
    val types: List<Type?>? = listOf(),
    @SerialName("weight")
    val weight: Int? = 0
) : Parcelable {
    @Serializable
    @Parcelize
    data class Ability(
        @SerialName("ability")
        val ability: Ability? = Ability(),
        @SerialName("is_hidden")
        val isHidden: Boolean? = false,
        @SerialName("slot")
        val slot: Int? = 0
    ) : Parcelable {
        @Serializable
        @Parcelize
        data class Ability(
            @SerialName("name")
            val name: String? = "",
            @SerialName("url")
            val url: String? = ""
        ) : Parcelable
    }

    @Serializable
    @Parcelize
    data class Cries(
        @SerialName("latest")
        val latest: String? = "",
        @SerialName("legacy")
        val legacy: String? = ""
    ) : Parcelable

    @Serializable
    @Parcelize
    data class Form(
        @SerialName("name")
        val name: String? = "",
        @SerialName("url")
        val url: String? = ""
    ) : Parcelable

    @Serializable
    @Parcelize
    data class GameIndice(
        @SerialName("game_index")
        val gameIndex: Int? = 0,
        @SerialName("version")
        val version: Version? = Version()
    ) : Parcelable {
        @Serializable
        @Parcelize
        data class Version(
            @SerialName("name")
            val name: String? = "",
            @SerialName("url")
            val url: String? = ""
        ) : Parcelable
    }

    @Serializable
    @Parcelize
    data class HeldItem(
        @SerialName("item")
        val item: Item? = Item(),
        @SerialName("version_details")
        val versionDetails: List<VersionDetail?>? = listOf()
    ) : Parcelable {
        @Serializable
        @Parcelize
        data class Item(
            @SerialName("name")
            val name: String? = "",
            @SerialName("url")
            val url: String? = ""
        ) : Parcelable

        @Serializable
        @Parcelize
        data class VersionDetail(
            @SerialName("rarity")
            val rarity: Int? = 0,
            @SerialName("version")
            val version: Version? = Version()
        ) : Parcelable {
            @Serializable
            @Parcelize
            data class Version(
                @SerialName("name")
                val name: String? = "",
                @SerialName("url")
                val url: String? = ""
            ) : Parcelable
        }
    }

    @Serializable
    @Parcelize
    data class Move(
        @SerialName("move")
        val move: Move? = Move(),
        @SerialName("version_group_details")
        val versionGroupDetails: List<VersionGroupDetail?>? = listOf()
    ) : Parcelable {
        @Serializable
        @Parcelize
        data class Move(
            @SerialName("name")
            val name: String? = "",
            @SerialName("url")
            val url: String? = ""
        ) : Parcelable

        @Serializable
        @Parcelize
        data class VersionGroupDetail(
            @SerialName("level_learned_at")
            val levelLearnedAt: Int? = 0,
            @SerialName("move_learn_method")
            val moveLearnMethod: MoveLearnMethod? = MoveLearnMethod(),
            @SerialName("version_group")
            val versionGroup: VersionGroup? = VersionGroup()
        ) : Parcelable {
            @Serializable
            @Parcelize
            data class MoveLearnMethod(
                @SerialName("name")
                val name: String? = "",
                @SerialName("url")
                val url: String? = ""
            ) : Parcelable

            @Serializable
            @Parcelize
            data class VersionGroup(
                @SerialName("name")
                val name: String? = "",
                @SerialName("url")
                val url: String? = ""
            ) : Parcelable
        }
    }

    @Serializable
    @Parcelize
    data class Species(
        @SerialName("name")
        val name: String? = "",
        @SerialName("url")
        val url: String? = ""
    ) : Parcelable

    @Serializable
    @Parcelize
    data class Sprites(
        @SerialName("back_default")
        val backDefault: String? = "",
        @SerialName("back_female")
        val backFemale: String? = "",
        @SerialName("back_shiny")
        val backShiny: String? = "",
        @SerialName("back_shiny_female")
        val backShinyFemale: String? = "",
        @SerialName("front_default")
        val frontDefault: String? = "https://wallpapers.com/images/featured-full/all-pokemon-pictures-bh730s8zr74xsc2p.jpg",
        @SerialName("front_female")
        val frontFemale: String? = "",
        @SerialName("front_shiny")
        val frontShiny: String? = "",
        @SerialName("front_shiny_female")
        val frontShinyFemale: String? = "",
        @SerialName("other")
        val other: Other? = Other(),
        @SerialName("versions")
        val versions: Versions? = Versions()
    ) : Parcelable {
        @Serializable
        @Parcelize
        data class Other(
            @SerialName("dream_world")
            val dreamWorld: DreamWorld? = DreamWorld(),
            @SerialName("home")
            val home: Home? = Home(),
            @SerialName("official-artwork")
            val officialArtwork: OfficialArtwork? = OfficialArtwork(),
            @SerialName("showdown")
            val showdown: Showdown? = Showdown()
        ) : Parcelable {
            @Serializable
            @Parcelize
            data class DreamWorld(
                @SerialName("front_default")
                val frontDefault: String? = "",

            ) : Parcelable

            @Serializable
            @Parcelize
            data class Home(
                @SerialName("front_default")
                val frontDefault: String? = "",
                @SerialName("front_female")
                val frontFemale: String? = "",
                @SerialName("front_shiny")
                val frontShiny: String? = "",
                @SerialName("front_shiny_female")
                val frontShinyFemale: String? = ""
            ) : Parcelable

            @Serializable
            @Parcelize
            data class OfficialArtwork(
                @SerialName("front_default")
                val frontDefault: String? = "",
                @SerialName("front_shiny")
                val frontShiny: String? = ""
            ) : Parcelable

            @Serializable
            @Parcelize
            data class Showdown(
                @SerialName("back_default")
                val backDefault: String? = "",
                @SerialName("back_female")
                val backFemale: String? = "",
                @SerialName("back_shiny")
                val backShiny: String? = "",

                @SerialName("front_default")
                val frontDefault: String? = "",
                @SerialName("front_female")
                val frontFemale: String? = "",
                @SerialName("front_shiny")
                val frontShiny: String? = "",
                @SerialName("front_shiny_female")
                val frontShinyFemale: String? = ""
            ) : Parcelable
        }

        @Serializable
        @Parcelize
        data class Versions(
            @SerialName("generation-i")
            val generationI: GenerationI? = GenerationI(),
            @SerialName("generation-ii")
            val generationIi: GenerationIi? = GenerationIi(),
            @SerialName("generation-iii")
            val generationIii: GenerationIii? = GenerationIii(),
            @SerialName("generation-iv")
            val generationIv: GenerationIv? = GenerationIv(),
            @SerialName("generation-v")
            val generationV: GenerationV? = GenerationV(),
            @SerialName("generation-vi")
            val generationVi: GenerationVi? = GenerationVi(),
            @SerialName("generation-vii")
            val generationVii: GenerationVii? = GenerationVii(),
            @SerialName("generation-viii")
            val generationViii: GenerationViii? = GenerationViii()
        ) : Parcelable {
            @Serializable
            @Parcelize
            data class GenerationI(
                @SerialName("red-blue")
                val redBlue: RedBlue? = RedBlue(),
                @SerialName("yellow")
                val yellow: Yellow? = Yellow()
            ) : Parcelable {
                @Serializable
                @Parcelize
                data class RedBlue(
                    @SerialName("back_default")
                    val backDefault: String? = "",
                    @SerialName("back_gray")
                    val backGray: String? = "",
                    @SerialName("back_transparent")
                    val backTransparent: String? = "",
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_gray")
                    val frontGray: String? = "",
                    @SerialName("front_transparent")
                    val frontTransparent: String? = ""
                ) : Parcelable

                @Serializable
                @Parcelize
                data class Yellow(
                    @SerialName("back_default")
                    val backDefault: String? = "",
                    @SerialName("back_gray")
                    val backGray: String? = "",
                    @SerialName("back_transparent")
                    val backTransparent: String? = "",
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_gray")
                    val frontGray: String? = "",
                    @SerialName("front_transparent")
                    val frontTransparent: String? = ""
                ) : Parcelable
            }

            @Serializable
            @Parcelize
            data class GenerationIi(
                @SerialName("crystal")
                val crystal: Crystal? = Crystal(),
                @SerialName("gold")
                val gold: Gold? = Gold(),
                @SerialName("silver")
                val silver: Silver? = Silver()
            ) : Parcelable {
                @Serializable
                @Parcelize
                data class Crystal(
                    @SerialName("back_default")
                    val backDefault: String? = "",
                    @SerialName("back_shiny")
                    val backShiny: String? = "",
                    @SerialName("back_shiny_transparent")
                    val backShinyTransparent: String? = "",
                    @SerialName("back_transparent")
                    val backTransparent: String? = "",
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_shiny")
                    val frontShiny: String? = "",
                    @SerialName("front_shiny_transparent")
                    val frontShinyTransparent: String? = "",
                    @SerialName("front_transparent")
                    val frontTransparent: String? = ""
                ) : Parcelable

                @Serializable
                @Parcelize
                data class Gold(
                    @SerialName("back_default")
                    val backDefault: String? = "",
                    @SerialName("back_shiny")
                    val backShiny: String? = "",
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_shiny")
                    val frontShiny: String? = "",
                    @SerialName("front_transparent")
                    val frontTransparent: String? = ""
                ) : Parcelable

                @Serializable
                @Parcelize
                data class Silver(
                    @SerialName("back_default")
                    val backDefault: String? = "",
                    @SerialName("back_shiny")
                    val backShiny: String? = "",
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_shiny")
                    val frontShiny: String? = "",
                    @SerialName("front_transparent")
                    val frontTransparent: String? = ""
                ) : Parcelable
            }

            @Serializable
            @Parcelize
            data class GenerationIii(
                @SerialName("emerald")
                val emerald: Emerald? = Emerald(),
                @SerialName("firered-leafgreen")
                val fireredLeafgreen: FireredLeafgreen? = FireredLeafgreen(),
                @SerialName("ruby-sapphire")
                val rubySapphire: RubySapphire? = RubySapphire()
            ) : Parcelable {
                @Serializable
                @Parcelize
                data class Emerald(
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_shiny")
                    val frontShiny: String? = ""
                ) : Parcelable

                @Serializable
                @Parcelize
                data class FireredLeafgreen(
                    @SerialName("back_default")
                    val backDefault: String? = "",
                    @SerialName("back_shiny")
                    val backShiny: String? = "",
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_shiny")
                    val frontShiny: String? = ""
                ) : Parcelable

                @Serializable
                @Parcelize
                data class RubySapphire(
                    @SerialName("back_default")
                    val backDefault: String? = "",
                    @SerialName("back_shiny")
                    val backShiny: String? = "",
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_shiny")
                    val frontShiny: String? = ""
                ) : Parcelable
            }

            @Serializable
            @Parcelize
            data class GenerationIv(
                @SerialName("diamond-pearl")
                val diamondPearl: DiamondPearl? = DiamondPearl(),
                @SerialName("heartgold-soulsilver")
                val heartgoldSoulsilver: HeartgoldSoulsilver? = HeartgoldSoulsilver(),
                @SerialName("platinum")
                val platinum: Platinum? = Platinum()
            ) : Parcelable {
                @Serializable
                @Parcelize
                data class DiamondPearl(
                    @SerialName("back_default")
                    val backDefault: String? = "",
                    @SerialName("back_female")
                    val backFemale: String? = "",
                    @SerialName("back_shiny")
                    val backShiny: String? = "",
                    @SerialName("back_shiny_female")
                    val backShinyFemale: String? = "",
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_female")
                    val frontFemale: String? = "",
                    @SerialName("front_shiny")
                    val frontShiny: String? = "",
                    @SerialName("front_shiny_female")
                    val frontShinyFemale: String? = ""
                ) : Parcelable

                @Serializable
                @Parcelize
                data class HeartgoldSoulsilver(
                    @SerialName("back_default")
                    val backDefault: String? = "",
                    @SerialName("back_female")
                    val backFemale: String? = "",
                    @SerialName("back_shiny")
                    val backShiny: String? = "",
                    @SerialName("back_shiny_female")
                    val backShinyFemale: String? = "",
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_female")
                    val frontFemale: String? = "",
                    @SerialName("front_shiny")
                    val frontShiny: String? = "",
                    @SerialName("front_shiny_female")
                    val frontShinyFemale: String? = ""
                ) : Parcelable

                @Serializable
                @Parcelize
                data class Platinum(
                    @SerialName("back_default")
                    val backDefault: String? = "",
                    @SerialName("back_female")
                    val backFemale: String? = "",
                    @SerialName("back_shiny")
                    val backShiny: String? = "",
                    @SerialName("back_shiny_female")
                    val backShinyFemale: String? = "",
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_female")
                    val frontFemale: String? = "",
                    @SerialName("front_shiny")
                    val frontShiny: String? = "",
                    @SerialName("front_shiny_female")
                    val frontShinyFemale: String? = ""
                ) : Parcelable
            }

            @Serializable
            @Parcelize
            data class GenerationV(
                @SerialName("black-white")
                val blackWhite: BlackWhite? = BlackWhite()
            ) : Parcelable {
                @Serializable
                @Parcelize
                data class BlackWhite(
                    @SerialName("animated")
                    val animated: Animated? = Animated(),
                    @SerialName("back_default")
                    val backDefault: String? = "",
                    @SerialName("back_female")
                    val backFemale: String? = "",
                    @SerialName("back_shiny")
                    val backShiny: String? = "",
                    @SerialName("back_shiny_female")
                    val backShinyFemale: String? = "",
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_female")
                    val frontFemale: String? = "",
                    @SerialName("front_shiny")
                    val frontShiny: String? = "",
                    @SerialName("front_shiny_female")
                    val frontShinyFemale: String? = ""
                ) : Parcelable {
                    @Serializable
                    @Parcelize
                    data class Animated(
                        @SerialName("back_default")
                        val backDefault: String? = "",
                        @SerialName("back_female")
                        val backFemale: String? = "",
                        @SerialName("back_shiny")
                        val backShiny: String? = "",
                        @SerialName("back_shiny_female")
                        val backShinyFemale: String? = "",
                        @SerialName("front_default")
                        val frontDefault: String? = "",
                        @SerialName("front_female")
                        val frontFemale: String? = "",
                        @SerialName("front_shiny")
                        val frontShiny: String? = "",
                        @SerialName("front_shiny_female")
                        val frontShinyFemale: String? = ""
                    ) : Parcelable
                }
            }

            @Serializable
            @Parcelize
            data class GenerationVi(
                @SerialName("omegaruby-alphasapphire")
                val omegarubyAlphasapphire: OmegarubyAlphasapphire? = OmegarubyAlphasapphire(),
                @SerialName("x-y")
                val xY: XY? = XY()
            ) : Parcelable {
                @Serializable
                @Parcelize
                data class OmegarubyAlphasapphire(
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_female")
                    val frontFemale: String? = "",
                    @SerialName("front_shiny")
                    val frontShiny: String? = "",
                    @SerialName("front_shiny_female")
                    val frontShinyFemale: String? = ""
                ) : Parcelable

                @Serializable
                @Parcelize
                data class XY(
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_female")
                    val frontFemale: String? = "",
                    @SerialName("front_shiny")
                    val frontShiny: String? = "",
                    @SerialName("front_shiny_female")
                    val frontShinyFemale: String? = ""
                ) : Parcelable
            }

            @Serializable
            @Parcelize
            data class GenerationVii(
                @SerialName("icons")
                val icons: Icons? = Icons(),
                @SerialName("ultra-sun-ultra-moon")
                val ultraSunUltraMoon: UltraSunUltraMoon? = UltraSunUltraMoon()
            ) : Parcelable {
                @Serializable
                @Parcelize
                data class Icons(
                    @SerialName("front_default")
                    val frontDefault: String? = "",

                ) : Parcelable

                @Serializable
                @Parcelize
                data class UltraSunUltraMoon(
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_female")
                    val frontFemale: String? = "",
                    @SerialName("front_shiny")
                    val frontShiny: String? = "",
                    @SerialName("front_shiny_female")
                    val frontShinyFemale: String? = ""
                ) : Parcelable
            }

            @Serializable
            @Parcelize
            data class GenerationViii(
                @SerialName("icons")
                val icons: Icons? = Icons()
            ) : Parcelable {
                @Serializable
                @Parcelize
                data class Icons(
                    @SerialName("front_default")
                    val frontDefault: String? = "",
                    @SerialName("front_female")
                    val frontFemale: String? = ""
                ) : Parcelable
            }
        }
    }

    @Serializable
    @Parcelize
    data class Stat(
        @SerialName("base_stat")
        val baseStat: Int? = 0,
        @SerialName("effort")
        val effort: Int? = 0,
        @SerialName("stat")
        val stat: Stat? = Stat()
    ) : Parcelable {
        @Serializable
        @Parcelize
        data class Stat(
            @SerialName("name")
            val name: String? = "",
            @SerialName("url")
            val url: String? = ""
        ) : Parcelable
    }

    @Serializable
    @Parcelize
    data class Type(
        @SerialName("slot")
        val slot: Int? = 0,
        @SerialName("type")
        val type: Type? = Type()
    ) : Parcelable {
        @Serializable
        @Parcelize
        data class Type(
            @SerialName("name")
            val name: String? = "",
            @SerialName("url")
            val url: String? = ""
        ) : Parcelable
    }
}