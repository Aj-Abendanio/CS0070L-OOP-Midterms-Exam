# Personalized Productivity Planner
A Java console application that lets you plan your day across fourcategories (College, Career, Hobby, Chores), detects overlapping tasks, shows your free time, and saves your schedule to a text file.

# Features
- Add tasks with a category, title, start time, and end time
- Rejects tasks that overlap an existing task (back-to-back tasks are allowed)
- Remove tasks by title
- View the schedule sorted by start time
- Show free time within a planning window you choose
- Random motivational messages
- Saves to and loads from schedule.txt automatically

# Compile and Run (Windows)
1. Open **Command Prompt** or **PowerShell**.
2. Change to the project folder. Replace the example path with your actual project location:

   ```powershell
   cd "C:\Users\YourName\Desktop\Programming\Java Midterm Lab"
   ```

3. Compile the Java files into an `out` folder:

   ```powershell
   New-Item -ItemType Directory -Force out
   javac -d out Schedulable.java Task.java CollegeTask.java CareerTask.java HobbyTask.java ChoreTask.java DailyPlanner.java ScheduleStorage.java MotivationGenerator.java PlannerApp.java
   ```

4. Run the application:

   ```powershell
   java -cp out PlannerApp
   ```