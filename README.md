# alphatimer

Interprets the raw data of the ARES21 Serial Transmission Protocol by [Swiss Timing](https://www.swisstiming.com/).

## Notes

The implementation is based on the the transmission protocol description version 1.00 from March 1998. Additional and
corrected information has been extracted from captured data during competitions.

## Status

| Chapter | Type                    | Status      |
| ------- | ----------------------- | ----------- |
| 1.2     | UNT4 New race           | -           |
| 1.2     | UNT4 Running time       | -           |
| 1.2     | UNT4 Net time           | -           |
| 1.2     | UNT4 Clear              | -           |
| 1.3     | GALACTICA Reaction time | -           |
| 2.2     | Message 1               | implemented |
| 2.2     | Message 2               | implemented |
| 3       | Records                 | -           |
| Apendix | Lap Counter             | -           |
| -       | Ping                    | implemented |

## Additional message information

The GALACTICA protocol also includes all messages from the UNT4 protocol.

Ping messages are similar to the Lap Counter messages, but are only used to signal activity. Most devices will blink
during incoming serial communication and therefore indicate a working connection.
