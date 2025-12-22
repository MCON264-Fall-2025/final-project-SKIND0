# Instructor Autograding Tests

This directory contains instructor-provided integration tests that are **hidden from students**.

## How It Works

1. **These tests are NOT visible** to students in their starter code
2. **During GitHub Actions autograding**, the workflow copies these tests into the test directory
3. **Tests are run automatically** when students push their code

## Files

- `VenueSelectionTest.java` - Tests venue selection logic (10 points)
- `GuestManagementTest.java` - Tests guest list management with LinkedList/Map (10 points)
- `SeatingAlgorithmTest.java` - Tests seating algorithm implementation (10 points)
- `TaskWorkflowTest.java` - Tests task queue and undo stack (10 points)

## Updating Tests

To modify instructor tests:
1. Edit the files in this `.github/tests/` directory
2. Tests will automatically be used in the next autograding run
3. Students cannot see or modify these tests

## Total Points

- Autograding integration tests: **40 points** (10 per component)
- Student unit tests coverage: **15 points** (via JaCoCo)
- README documentation: **5 points** (via check_readme.py)

