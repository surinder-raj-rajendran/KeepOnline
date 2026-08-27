# KeepOnline
This is to keep microsoft teams online

A small Python script that moves the mouse pointer by a tiny random amount
at random intervals, so status-based apps like Microsoft Teams keep showing
you as "Active" instead of flipping to "Away"/"Idle".

## Usage

```bash
pip install -r requirements.txt
python keep_online.py
```

Optional flags:

```bash
python keep_online.py --min-interval 20 --max-interval 90 --distance 15
```

- `--min-interval` / `--max-interval`: random wait range (seconds) between movements (default 30-120)
- `--distance`: max pixels the cursor is nudged in any direction (default 10)

Press `Ctrl+C` to stop. Moving the mouse into a screen corner also aborts
immediately (pyautogui's fail-safe).
