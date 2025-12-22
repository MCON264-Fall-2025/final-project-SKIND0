import os
import sys
import re

def check_readme():
    readme_path = 'README.md'

    if not os.path.exists(readme_path):
        print("❌ README.md not found")
        sys.exit(1)

    with open(readme_path, 'r', encoding='utf-8') as f:
        content = f.read()

    required_sections = [
        r'data\s*structure',
        r'big[\s-]*o|complexity|time\s*complexity',
        r'justif(y|ication)|explain|rationale'
    ]

    found = []
    for pattern in required_sections:
        if re.search(pattern, content, re.IGNORECASE):
            found.append(True)
        else:
            found.append(False)

    word_count = len(content.split())

    print(f"README word count: {word_count}")
    print(f"Required sections found: {sum(found)}/{len(required_sections)}")

    if sum(found) >= 2 and word_count >= 300:
        print("✅ README meets requirements")
        sys.exit(0)
    else:
        print("❌ README needs more detail on data structures and Big-O analysis")
        sys.exit(1)

if __name__ == '__main__':
    check_readme()

