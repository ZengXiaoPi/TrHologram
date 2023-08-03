![animate_60fps_.gif](https://i.loli.net/2021/08/19/wnVevcdAjBr2NXb.gif)
# TrHologram

#### Modern & Lightweight Holographic-Plugin
![](https://img.shields.io/github/last-commit/Micalhl/TrHologram?logo=artstation&style=for-the-badge&color=9266CC)![](https://img.shields.io/github/issues/Micalhl/TrHologram?style=for-the-badge&logo=slashdot)![](https://img.shields.io/github/release/Arasple/TrHologram?style=for-the-badge&color=00C58E&logo=ionic)

---

![bStats](https://bstats.org/signatures/bukkit/TrHologram.svg)

---

### Oops!
Because of my studies, the updates is slowing down. Sorry :(

### Features

- **Highly Optimized**
    - 100% Packet-based hologram (armorstand, item), no-lag
    - Async update tasks

- **Light & Powerful**
    - Individual update task for each line
    - Custom view distance & view condition
    - Custom line spacing and offset for individual line  
    - Support to display floating item with custom texture
    - Interactive holograms (4 clicktypes integrated)
    - PlaceholderAPI and custom functions support

- **API**
    - Friendly developer API, create dynamic holograms easily

---

### API

#### Dependency

In Maven:
```xml
    <repositories>
      <repository>
        <id>roselle-public</id>
        <url>https://repo.mcage.cn/repository/maven-public/</url>
      </repository>
    </repositories>

    <dependencies>
      <dependency>
        <groupId>me.arasple</groupId>
        <artifactId>trmenu</artifactId>
        <version>3.0-PRE-20</version>
        <classifier>pure</classifier>
        <scope>provided</scope>
      </dependency>
    </dependencies>
```

In Gradle Kotlin DSL:
```kotlin
repositories {
  maven("https://repo.mcage.cn/repository/maven-public/")
}
dependencies {
  compileOnly("me.arasple:TrMenu:3.0-PRE-20:pure")
}

```

#### Usagea

```java
class Demo {

    public void display(Player viewer) {
        Hologram hologram = TrHologramAPI
                .builder(viewer.getLocation())
                .append("Hello World")
                .append(player -> player.getInventory().getItemInMainHand(), 40)
                .interspace(0.5)
                .append("Time: %server_time_ss%", 20)
                .build();

        hologram.refreshVisibility(viewer);

        TextHologram line = hologram.getTextLine(0);
        line.setText("Hello TrHologram");
    }

}
```
