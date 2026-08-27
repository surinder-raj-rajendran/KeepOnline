# KeepOnline
This is to keep microsoft teams online

A small Java program that moves the mouse pointer by a tiny random amount
at random intervals, so status-based apps like Microsoft Teams keep showing
you as "Active" instead of flipping to "Away"/"Idle". Uses only the JDK's
built-in `java.awt.Robot` — no external dependencies.

## Usage

```bash
javac KeepOnline.java
java KeepOnline
```

Optional flags:

```bash
java KeepOnline --min-interval 20 --max-interval 90 --distance 15
```

- `--min-interval` / `--max-interval`: random wait range (seconds) between movements (default 30-120)
- `--distance`: max pixels the cursor is nudged in any direction (default 10)

Press `Ctrl+C` to stop.
