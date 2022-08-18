plugins {
    `maven-publish`
    id("java")
    id("io.izzel.taboolib") version "1.34"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}
    
group = "me.arasple.mc.trhologram"
version = "2.4-pre29"
description = "Modern & Advanced Hologram-Plugin for Minecraft Servers"

taboolib {
    install(
        "common",
        "common-5",
        "platform-bukkit",
        "module-configuration",
        "module-chat",
        "module-lang",
        "module-nms",
        "module-nms-util",
        "module-metrics",
        "module-kether"
    )

    description {
        contributors {
            name("Arasple")
        }
        dependencies {
            name("PlaceholderAPI").optional(true)
            name("TrMenu").optional(true)
            name("SkinsRestorer").optional(true)
            name("Multiverse-Core").loadafter(true)
            name("PlotSquared").loadafter(true)
        }
    }

    classifier = null
    version = "6.0.9-65"
}

repositories {
    mavenCentral()
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib")
    compileOnly("ink.ptms:nms-all:1.0.0")
    compileOnly("ink.ptms.core:v11900:11900-minimize:mapped")
    compileOnly("ink.ptms.core:v11900:11900-minimize:universal")
    compileOnly("ink.ptms.core:v11800:11800-minimize:mapped")
    compileOnly("ink.ptms.core:v11800:11800-minimize:universal")
    compileOnly("ink.ptms.core:v11701:11701:mapped")
    compileOnly("ink.ptms.core:v11701:11701:universal")
    compileOnly("me.clip:placeholderapi:2.10.9")
    compileOnly(fileTree("libs"))
}


tasks.shadowJar {
    dependencies {
        taboolib.modules.forEach { exclude(dependency("io.izzel:taboolib:${taboolib.version}:$it")) }
        exclude(dependency("com.google.code.gson:gson:2.8.6"))
        exclude(dependency("org.bstats:bstats-bukkit:1.5"))

        exclude("data")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/maven")
        exclude("lang")
        exclude("holograms")
        exclude("*.yml")
    }
    relocate("taboolib", "${project.group}.taboolib")
    archiveClassifier.set("pure")
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("shadow") {
            shadow.component(this)
            groupId = "me.arasple"
        }
    }
    repositories {
        maven {
            url = uri("https://repo.iroselle.com/repository/maven-releases/")
            credentials {
                file("access.txt").also {
                    if (!it.exists()) return@credentials
                }.readLines().apply {
                    username = this[0]
                    password = this[1]
                }
            }
        }
    }
}