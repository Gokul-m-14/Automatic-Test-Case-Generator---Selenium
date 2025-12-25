# Selenium AutoTest Upgrade

Generates clear test cases and exports a separate Excel (.xlsx) file per website.

Build:
  mvn clean package

Run:
  java -jar target/selenium-autotest-upgrade-1.0-SNAPSHOT-shaded.jar <target-url>

Output:
  output/<sanitized_website>_testcases.xlsx
