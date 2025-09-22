# Rama2 User Guide



![Alt text](Ui.png "Optional title")

Rama2 ([_rama-rama_](https://en.m.wiktionary.org/wiki/rama-rama)) means _butterfly_ in Malay.

> Butterflies can be pretty
>
> -somebody 

## Adding todo

Adds a todo to the list of tasks.

Example: `todo <name>`
```
> todo laundry
     Got it. I've added this task:
       [T][ ] laundry
     Now you have 6 tasks in the list.
```


## Adding deadlines

Adds a deadline to the list of tasks.

`deadline <name> /by <datetime>`

Example: 
```
> deadline week6 laundrys /by Thu
     Got it. I've added this task:
       [D][ ] week6 laundrys (by: Thu)
     Now you have 8 tasks in the list.

```


## Adding events

Adds an event to the list of tasks.

Example: `event <name> /from <datetime>  /to <datetime>`   <!--(`/from` and `/to` can be swapped.)-->
```
> event Dentist /from 3pm /to 5pm
     Got it. I've added this task:
       [E][ ] Dentist (from: 3pm to: 5pm)
     Now you have 9 tasks in the list.
```

## List tasks

Lists tasks.

`list`
```
> list
     Here are the tasks in your list:
     1.[D][ ] week6 iP (by: Thu)
     2.[T][ ] laundry
     3.[D][ ] week6 laundrys (by: Thu)
     4.[E][ ] Dentist (from: 3pm to: 5pm)
```

## Find tasks

Find a task by part of its name

Example: `find <search string>`
```
> find laundry
     Here are the tasks in your list:
     1.[T][ ] laundry
     2.[D][ ] week6 laundrys (by: Thu)
```

## Mark a task done

Mark a task as done, will be shown as `[X]`

Example: `mark <task number in list>`
```
> mark 3
     Nice! I've marked this task as done:
       [D][X] week6 laundrys (by: Thu)

> list
     Here are the tasks in your list:
     1.[D][ ] week6 iP (by: Thu)
     2.[T][ ] laundry
     3.[D][X] week6 laundrys (by: Thu)
     4.[E][ ] Dentist (from: 3pm to: 5pm)

```

## Mark a task __not__ done

Mark a task as done, will be shown as `[ ]`

Example: `unmark  <task number in list>`
```
> unmark 3
     OK, I've marked this task as not done yet:
       [D][ ] week6 laundrys (by: Thu)

> list
     Here are the tasks in your list:
     1.[D][ ] week6 iP (by: Thu)
     2.[T][ ] laundry
     3.[D][ ] week6 laundrys (by: Thu)
     4.[E][ ] Dentist (from: 3pm to: 5pm)

```

## Exit

Exits

`bye `
```
> bye
     Bye. Hope to see you again soon!
```

## Saving and Loading
The program automatically saves to `Rama2.txt` after every change, and loads from there before startup if it is available.

## Fat self-contained (JavaFX-containing) Jar file
Available at `build\libs\ramarama.jar`
