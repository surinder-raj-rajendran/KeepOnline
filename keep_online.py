#!/usr/bin/env python3
"""Move the mouse pointer randomly at random intervals to keep Microsoft Teams
(or any status-based app) showing an "Active" presence instead of going Idle.

Usage:
    python keep_online.py
    python keep_online.py --min-interval 20 --max-interval 90 --distance 15

Press Ctrl+C to stop. As a safety net, pyautogui's fail-safe is left enabled:
slam the mouse into a screen corner to abort immediately.
"""

import argparse
import random
import sys
import time
from datetime import datetime

try:
    import pyautogui
except ImportError:
    sys.exit(
        "pyautogui is required. Install it with:\n"
        "    pip install pyautogui"
    )


def log(message: str) -> None:
    timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    print(f"[{timestamp}] {message}", flush=True)


def jiggle_mouse(distance: int) -> None:
    """Nudge the mouse a small random amount and back, so the cursor ends up
    roughly where it started."""
    dx = random.randint(-distance, distance)
    dy = random.randint(-distance, distance)
    duration = random.uniform(0.1, 0.4)

    pyautogui.moveRel(dx, dy, duration=duration)
    time.sleep(random.uniform(0.1, 0.3))
    pyautogui.moveRel(-dx, -dy, duration=duration)


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "--min-interval", type=float, default=30,
        help="Minimum seconds to wait between movements (default: 30)",
    )
    parser.add_argument(
        "--max-interval", type=float, default=120,
        help="Maximum seconds to wait between movements (default: 120)",
    )
    parser.add_argument(
        "--distance", type=int, default=10,
        help="Max pixels to move the cursor in any direction (default: 10)",
    )
    args = parser.parse_args()

    if args.min_interval <= 0 or args.max_interval < args.min_interval:
        sys.exit("Invalid interval range: require 0 < min-interval <= max-interval")

    pyautogui.FAILSAFE = True  # move mouse to a screen corner to abort

    log("Starting mouse-jiggler to keep Microsoft Teams active. Press Ctrl+C to stop.")
    log(f"Interval: {args.min_interval}-{args.max_interval}s, distance: {args.distance}px")

    try:
        while True:
            wait_time = random.uniform(args.min_interval, args.max_interval)
            time.sleep(wait_time)
            jiggle_mouse(args.distance)
            log(f"Jiggled mouse, next move in ~{wait_time:.0f}s")
    except KeyboardInterrupt:
        log("Stopped by user.")
    except pyautogui.FailSafeException:
        log("Fail-safe triggered (mouse moved to a screen corner). Stopped.")


if __name__ == "__main__":
    main()
