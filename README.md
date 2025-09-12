# Rama2 (CS2103T iP)

Rama2 ([_rama-rama_](https://en.m.wiktionary.org/wiki/rama-rama)) means _butterfly_ in Malay.

> Butterflies can be pretty
>
> -nobody 

## AI declaration
https://chatgpt.com/s/t_68b7e504ba448191a1c3625180dc1b71,

Used AI to some extent for Level-4 onwards, but read through code and ran checks (had to make one minor tweak and fix CRLF->LF)

## Guide

![Alt text](docs\Ui.png "Optional title")

## Features/commands
- todo
- deadline
- `\event name \from __ \to __` (`\to ... \from ...` works too :))
1. \from: starting datetime
2. \to: ending datetime
- list
- find
- mark
- unmark
- bye

The program has a command-line interface too:
```
list
     Here are the tasks in your list:
     1.[D][ ] Testing «τα???σ»: 1<2 & 4+1>3, now 20% off!        
     2.[E][ ] b (from: b to: c)
     3.[D][ ] c (by: r)
     4.[E][ ] x (from: y y to: z)
     5.[E][ ] m (from: noo oo to: n)
     6.[E][ ] e (from: 2025-10-12 to: 3)

deadline laundry /by 2025-10-03
     Got it. I've added this task:
       [D][ ] laundry (by: Oct 3 2025)
     Now you have 7 tasks in the list.

list
     Here are the tasks in your list:
     1.[D][ ] Testing «τα???σ»: 1<2 & 4+1>3, now 20% off!
     2.[E][ ] b (from: b to: c)
     3.[D][ ] c (by: r)
     4.[E][ ] x (from: y y to: z)
     5.[E][ ] m (from: noo oo to: n)
     6.[E][ ] e (from: 2025-10-12 to: 3)
     7.[D][ ] laundry (by: Oct 3 2025)
```
