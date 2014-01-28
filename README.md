![Alt](https://raw.github.com/nthmain/hobbled-tone/tree/master/images/hobbled-tone.png)

# Hobbled-Tone Project for NewToneHome

This repository contains the source code for the NewToneHome project,
which consists of scrips designed for the Raspberry Pi (running Arch Linux Arm),
an Android app for home monitoring, and instructions for installation.

## License
[MIT License](http://opensource.org/licenses/MIT)

## Status of Project
This project is currently in very early development stages. Instructions
and source code will be heavily modified and added to as the project
evolves.

##Contributing
As stated earlier, it will be difficult to contribute until a more stable
code base is reached. Any contributions are greatly appreciated and welcomed.
=================

###Project Overview
----------------
I have a 60's vintage Newtone Intercom System. The main unit is where I am currently
focussed, a large box on the wall in the kitchen with plenty of room for a Raspberry Pi,
small display, and hardware interfaces to the existing vintage switches and knobs.

This project is someone of a one-off from many existing Raspberry Pi Smart Home / Home Automation
builds that currently exist. I intend to use the existing Newtone hardware switches instead of
relying only on an Android tablet connected to the Pi. The Pi will run a 7" display, but with no
input devices other than the hardware switches.

The transformer, tubes, and any unnecessary hardware will be removed from the Newtone case,
making way for the Pi and it's supporting hardware. An arduino of some sort is planned to be added
to monitor the garage door.

Currently this project is planned to be a local monitor and local control, with no internet connectivity.
I am not looking to have every aspect of my home accessable anywhere in the world quite yet.
Once the system is efficiently montoring the home systems and controlling what it needs, expansions may be made.

Commonality
-----------
I do not wish to develop this as a one-off system that no one else can utilize. I plan on adapting the
software installation to be tailored to various units and hardware interfaces.

The goal is to have one single global system config file listing the devices on the monitoring network,
whether the devices are interfaced via Ethernet, Bluetooth, Zigbee, RS-485, etc.

Hardware
--------
The hardware schematics for the system will be developed along with the software. Below is a basic example of what
a typical Rev. 0 hardware layout is planned to look like.




                                                                       +-----------------------+
      +---------------+                               RS-232?          |                       |
      |               |                              +---------------> |   Arduino based       |
      |  Newtone      |         GPIO                 |                 |   Garage Door Monitor |
      |  Hardware     |+-------------+               |                 +-----------------------+
      |               |              v               +
      +---------------+           +------------------------------+
                                  |                              |
      +---------------+           |                              |
      |               |      GPIO |   Raspberry Pi               |
      | *New* Radio HW|<---------+|                              |
      |               |           |     Control/Monitor GPIO     |
      +---------------+           |                              |
                                  |                              |
                                  |                              |
                                  +------------------------------+
                                                ^
                                                +
                                                v
                                         +--------------+
        +--------------------+           | Home Network |
        | Android App        | <+-----+> +--------------+
        | Monitor/Log/Control|
        +--------------------+


