# HTWG AIN3 SE 
Scala - Hero Project
=====================================================
## The journey of our first Scala based video game
`INFO:` If you want to clone the repository and play the game with GUI, set the environment variable UI_TYPE=full <br>
[HowToPlay - YoutTube](https://youtu.be/xgbU-B7nY3g)


![](src/main/scala/de/htwg/se/aview/Graphics/UI/Font.png)

Code written: [Alina Göttig](https://github.com/AlinaGoettig) & [Ronny Klotz](https://github.com/ShuraBlack)

Graphic design: Ronny Klotz

Hero is based on the classic strategy video game series "Heroes of Might and Magic". 
Our goal is to make a stand alone out of the combat system, in which both main factions, Castle and Inferno fight each other.
This fight represents the epic battle between angels and demons.

The player will controll one of the choisen sides on a tile-like battleground. Each player get 7 unique creatures with
different values, like health points, damage amount, movment speed, etc. to fight until somebody got nothing left.

[![Hero - Project release trailer](https://s12.directupload.net/images/210116/yfv8fb55.png)](https://youtu.be/WEJsVZXKBGw "Hero - Project release trailer")

## HowToPlay
![Menu](https://s12.directupload.net/images/210123/rj5drxze.gif)
<br> Start up a new game or load an already existing save game in the main menu

![Sides](https://s12.directupload.net/images/210123/oy9xmx5u.gif)
<br> Each player choose a side at the beginning of a new game

![Move](https://s12.directupload.net/images/210123/nexl58h2.gif)
<br> Click on a black frame to move to the position

![Attack ranged](https://s12.directupload.net/images/210123/qfwuhx9n.gif)
<br> Ranged creatures can attack from everywhere

![Attack melee](https://s12.directupload.net/images/210123/pk65or2n.gif)
<br> while melee creature need to have a black frame near them to be attackable

![Win](https://s12.directupload.net/images/210123/nht7qkzs.gif)
<br> Kill all creatures of a side to win

## Graphics

![Castle](https://s12.directupload.net/images/210123/iq2u8twz.png)
![Inferno](https://s12.directupload.net/images/210123/j7g4vxtw.png)

## Project information

This project was developed in the course of the third semester at the HTWG-Konstanz (Applied Computer Science) in the lecture
Software Engineering.

Type | Version
:--- | ---:
Java JDK  | 1.8 
Scala | 2.12.7
Sbt | 0.13.18

Dependencies |
:--- |
Junit  |
Scala Swing |
Scalastic |
Scala Guice |
Scala Xml |
Play Json |
Util Audio |

The following tasks were completed in the course of the lecture

<details>
           <summary>Tasks</summary>
           <summary>Project Setup with SBT</summary>
           <p>https://moodle.htwg-konstanz.de/moodle/mod/url/view.php?id=122863</p>
           <summary>Version Control Systems - Git</summary>
           <p>https://moodle.htwg-konstanz.de/moodle/mod/url/view.php?id=126192</p>
           <summary>Agile Development</summary>
           <p>https://moodle.htwg-konstanz.de/moodle/mod/url/view.php?id=24751</p>
           <summary>Text User Interface</summary>
           <p>https://moodle.htwg-konstanz.de/moodle/mod/url/view.php?id=126809</p>
           <summary>MVC Architecture</summary>
           <p>https://moodle.htwg-konstanz.de/moodle/mod/url/view.php?id=127348</p>
           <summary>Continuous Deployment</summary>
           <p>https://moodle.htwg-konstanz.de/moodle/mod/url/view.php?id=127736</p>
           <summary>Design Pattern</summary>
           <p>https://moodle.htwg-konstanz.de/moodle/mod/url/view.php?id=128269</p>
           <summary>Undo/Redo Manager</summary>
           <p>https://moodle.htwg-konstanz.de/moodle/mod/url/view.php?id=128270</p>
           <summary>Graphical User Interface</summary>
           <p>https://moodle.htwg-konstanz.de/moodle/mod/url/view.php?id=129369</p>
           <summary>Components</summary>
           <p>https://moodle.htwg-konstanz.de/moodle/mod/url/view.php?id=129894</p>
           <summary>Dependency Injection</summary>
           <p>https://moodle.htwg-konstanz.de/moodle/mod/url/view.php?id=130308</p>
           <summary>FileIO</summary>
           <p>https://moodle.htwg-konstanz.de/moodle/mod/url/view.php?id=130751</p>
           <summary>Docker</summary>
           <p>https://moodle.htwg-konstanz.de/moodle/mod/url/view.php?id=131651</p>
</details>

## Links about the main game

[Wikipedia](https://de.wikipedia.org/wiki/Heroes_of_Might_%26_Magic_3)

[Publisher](https://www.ubisoft.com/de-de/game/heroes-of-might-and-magic-3-hd)

[Buy the Game (Gog.com)](https://www.gog.com/game/heroes_of_might_and_magic_3_complete_edition)


## Coverage in Master Github branch:

[![Build Status](https://travis-ci.org/AlinaGoettig/hero.svg?branch=master)](https://travis-ci.org/AlinaGoettig/hero)
[![Coverage Status](https://coveralls.io/repos/github/AlinaGoettig/hero/badge.svg?branch=master)](https://coveralls.io/github/AlinaGoettig/hero?branch=master)

## Coverage in TextUI Github branch:

[![Build Status](https://travis-ci.org/AlinaGoettig/hero.svg?branch=master)](https://travis-ci.org/AlinaGoettig/hero)
[![Coverage Status](https://coveralls.io/repos/github/AlinaGoettig/hero/badge.svg?branch=TextUI)](https://coveralls.io/github/AlinaGoettig/hero?branch=TextUI)
