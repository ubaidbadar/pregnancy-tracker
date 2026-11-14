#!/usr/bin/env python3
"""Validate Bloom Android string resources and the shipped Urdu launch flow."""

from pathlib import Path
import re
import sys
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
JAVA = ROOT / "app" / "src" / "main" / "java" / "app" / "restora" / "pregnancytracker" / "ui"
RES = ROOT / "app" / "src" / "main" / "res"

EN_XML = RES / "values" / "strings.xml"
UR_XML = RES / "values-ur" / "strings.xml"


def string_names(path: Path) -> set[str]:
    document = ET.parse(path).getroot()
    return {
        element.attrib["name"]
        for element in document.findall("string")
        if "name" in element.attrib
    }


def resource_references(source: str) -> set[str]:
    return set(re.findall(r"R\.string\.([A-Za-z0-9_]+)", source))


def read(path: Path) -> str:
    return path.read_text(encoding="utf-8")


english = string_names(EN_XML)
urdu = string_names(UR_XML)
all_kotlin = "\n".join(read(path) for path in JAVA.rglob("*.kt"))
referenced = resource_references(all_kotlin)

missing_english = sorted(referenced - english)
if missing_english:
    print("ERROR: Kotlin references missing English string resources:")
    print("\n".join(f"  - {name}" for name in missing_english))
    sys.exit(1)

onboarding = read(JAVA / "screens" / "OnboardingScreens.kt")
main_screens = read(JAVA / "screens" / "MainScreens.kt")


def section(source: str, start: str, end: str) -> str:
    start_index = source.index(start)
    end_index = source.index(end, start_index)
    return source[start_index:end_index]


urdu_launch_flow = (
    resource_references(onboarding)
    | resource_references(section(main_screens, "fun TodayScreen", "fun CheckInDoneScreen"))
    | resource_references(section(main_screens, "fun CheckInDoneScreen", "fun WeekStoryScreen"))
    | {"nav_today", "nav_journal", "nav_tools", "nav_plan"}
)
missing_urdu_launch_flow = sorted(urdu_launch_flow - urdu)
if missing_urdu_launch_flow:
    print("ERROR: Urdu onboarding/Today/check-in flow is missing translations:")
    print("\n".join(f"  - {name}" for name in missing_urdu_launch_flow))
    sys.exit(1)

other_missing_urdu = sorted(referenced - urdu - urdu_launch_flow)
print(
    f"PASS: {len(referenced)} referenced English strings exist; "
    f"{len(urdu_launch_flow)} launch-flow strings are translated to Urdu."
)
if other_missing_urdu:
    print(
        f"NOTICE: {len(other_missing_urdu)} strings in later screens still fall back to English."
    )
