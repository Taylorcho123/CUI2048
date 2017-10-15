import distorm3
import sys
#import argparse
import hexdump

from distorm3 import Decode, Decode16Bits, Decode32Bits, Decode64Bits


class DNarae:

    # Inform Usage
    # Use Command Line Argument
    def __init__(self):
        print("______ _   _      ")                
        print("|  _  \ \ | |                     ")
        print("| | | |  \| | __ _ _ __ __ _  ___ ")
        print("| | | | . ` |/ _` | '__/ _` |/ _ \")
        print("| |/ /| |\  | (_| | | | (_| |  __/")
        print("|___/ \_| \_/\__,_|_|  \__,_|\___|")
        print()
        print("Welcome to the DNarae v 0.01.0.")
        print()
        print("Usage:")
        print("    python DNarae.py open [filename|-]")
        print()
        print("Options: ")
        print("    -h, --help     show this help message and exit")
        print("    -r, --restore  restore binary from hex dump")
        print()

        if len(sys.argv) is 1:
            exit();

        if (sys.argv[1] == "open" || sys.argv[1] == "OPEN"):
            l = Decode(0x100, open(sys.argv[2], "rb").read(), Decode16Bits)

        else :
            print("ERROR: Invalid argument/option - '"+sys.argv[1]:"'")
            print("Type \"open [..path..]\" or \"OPEN [..path..]\" for usage.")


    # Print in the 'Hexdump' box
    def dn_hdump(self):
        # Just One of This Module Usage.
        # Plase Delete After Release the First Version
        '''
        message = 'интерференция'
        hexdump.hexdump(message.encode('utf8'), 'print')
        '''

        # Use "hexdump" module
        path1 = open("C:\SDL.dll", "rb")
        for i in path1:
            hexdump.hexdump(i, 'print')


    def dn_disassembler(self):
        # Print in the 'Disassembler' box
        for i in l:
            print("0x%08x (%02x) " % (i[0],  i[1]), end='')
            ch1 = i[3].decode('utf8')
            ch2 = i[2].decode('utf8')
            print("%-20s " %ch1, end='')
            print("%s" %ch2)
