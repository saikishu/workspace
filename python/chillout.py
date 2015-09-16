#!/usr/bin/python

#
# A program to prompt frequent breaks
# OS: Windows
#

import time
import ctypes

total_hours = 24
frequency_in_hours = 2
elapsed_hours = 0;

MB_OK = 0x0

while (elapsed_hours <= total_hours):
    time.sleep(frequency_in_hours*60*60)
    ctypes.windll.user32.MessageBoxA(0, "Relax Refresh Reboot", "Take a break!", MB_OK)
    elapsed_hours = elapsed_hours + frequency_in_hours
