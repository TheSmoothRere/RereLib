import io.github.smootheez.LoaderType
import io.github.smootheez.curseforge.EnvironmentType

plugins {
    id("net.fabricmc.fabric-loom")
    id("io.github.smootheez.mc-mod-publisher")
    `maven-publish`
//    signing
}

fun prop(name: String): String {
    val value = project.findProperty(name)
        ?: error("Required property '$name' is missing from gradle.properties!")
    return value.toString()
}

val modVersion = prop("modVersion")
val modId = prop("modId")
val modName = prop("modName")
val modDescription = prop("modDescription")
val modAuthor = prop("modAuthor")
val modLicense = prop("modLicense")
val minecraftVersion = prop("minecraftVersion")
val fabricApiVersion = prop("fabricApiVersion")
val javaVersion = prop("javaVersion")

val modReleaseType = prop("modReleaseType")
val modrinthId = prop("modrinthId")
val curseforgeId = prop("curseforgeId")

val mcModVersion = "$modVersion+$minecraftVersion"
version = mcModVersion

base {
    archivesName.set(modId)
}

loom {
    splitEnvironmentSourceSets()

    mods {
        create(modId) {
            sourceSet(sourceSets.getByName("main"))
            sourceSet(sourceSets.getByName("client"))
        }
    }

    runs {
        named("client") {
            client()
            generateRunConfig.set(true)
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    implementation(libs.fabric.loader)

    implementation(provider {
        include(fabricApi.module("fabric-api-base", fabricApiVersion))
        include(fabricApi.module("fabric-resource-loader-v1", fabricApiVersion))
    })
}

tasks.processResources {
    val resourceReplacements = mapOf(
        "version" to modVersion,
        "id" to modId,
        "name" to modName,
        "description" to modDescription,
        "author" to modAuthor,
        "license" to modLicense,
        "minecraftVersion" to minecraftVersion,
        "fabricApiVersion" to fabricApiVersion,
        "javaVersion" to javaVersion
    )

    inputs.properties(resourceReplacements)
    filesMatching("fabric.mod.json") {
        expand(resourceReplacements)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(javaVersion.toInt())
}

tasks.jar {
    val projectName = modId
    inputs.property("projectName", projectName)

    from(rootProject.file("LICENSE")) {
        rename { "${it}-$projectName" }
    }
}

java {
    withSourcesJar()
    withJavadocJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

mcModPublisher {
    displayName = "$modName $mcModVersion"
    version = mcModVersion
    releaseType = modReleaseType
    changelog = rootProject.file("changelogs/v$mcModVersion.md").readText()
    files.from(tasks.named("jar"))
    gameVersions.addAll(listOf("26.1", "26.1.1", "26.1.2"))
    loaders.addAll(listOf(LoaderType.FABRIC))

    curseforge {
        token = System.getenv("CURSEFORGE_TOKEN")
        projectId = curseforgeId
        environmentType = listOf(EnvironmentType.CLIENT)
    }

    modrinth {
        token = System.getenv("MODRINTH_TOKEN")
        projectId = modrinthId
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = project.group.toString()
            artifactId = modId
            version = mcModVersion

            from(components["java"])

            // Maven Central requires comprehensive POM metadata
            pom {
                name.set(modName)
                description.set(modDescription)
                url.set("https://github.com/$modAuthor/$modName") // Change to your repo URL

                licenses {
                    license {
                        name.set(modLicense)
                        url.set("https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set(modAuthor)
                        name.set("Rere")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/$modAuthor/$modName.git")
                    developerConnection.set("scm:git:ssh://github.com/$modAuthor/$modName.git")
                    url.set("https://github.com/$modAuthor/$modName")
                }
            }
        }
    }

    /*repositories {
        maven {
            name = "Sonatype"
            url = uri("https://central.sonatype.com/service/local/staging/deploy/maven2/")
            credentials {
                username = project.findProperty("sonatypeUsername")?.toString()
                password = project.findProperty("sonatypePassword")?.toString()
            }
        }
    }*/
}

/*signing {
    val tasksToRun = gradle.startParameter.taskNames
    if (tasksToRun.any { it.contains("publish") }) {
        val pgpKey = System.getenv("SIGNING_KEY") ?: project.findProperty("signing.key")?.toString()
        val pgpPassword = System.getenv("SIGNING_PASSWORD") ?: project.findProperty("signing.password")?.toString()

        if (pgpKey != null && pgpPassword != null) {
            useInMemoryPgpKeys(pgpKey, pgpPassword)
            sign(publishing.publications["mavenJava"])
        }
    }
}*/
