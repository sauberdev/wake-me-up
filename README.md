# wake-me-up
## Introduction
A small android background service to wake up the device when you are near to it. It will wake up the
device when you are moving around it using data provided by light sensor as alternative for devices
which doesn't have a front camera for motion detection.

## Description
To wake up a device was used a light sensor available almost in every device. The service doesn't 
have any UI and after start automatically goes to background mode. It uses partial wake lock in order
to prevent that service will be killed when device goes in a sleep state. When value of light
sensor changes for meaningful value it will wake up device using WakeUp lock and would release it 
immediately what allow to device turn off screen if there is no activity. That time can be configured
in display settings.

## Limitations
- It was tested on emulated device and android tablet with a version 7.0