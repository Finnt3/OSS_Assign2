#!/bin/bash

echo “This information is brought to you by $0.”  > sysFile.txt

#Welcome the user >> sysFile.txt
echo “Welcome, $USER” >> sysFile.txt
echo >> sysFile.txt
echo “Today is `date`.”  >> sysFile.txt
echo >> sysFile.txt
grep 'cpu ' /proc/stat | awk '{usage=($2+$4)*100/($2+$4+$5)} END {print usage}' >> sysFile.txt

#Currently active users. >> sysFile.txt
echo “Following users are presently active:” >> sysFile.txt
w | cut -d ' ' -f 1 | grep -v USER | sort -u >> sysFile.txt
echo >> sysFile.txt

#System information using uname command >> sysFile.txt
echo “This is `uname -s` running on a `uname -m` processor.” >> sysFile.txt
echo >> sysFile.txt

#Information of uptime >> sysFile.txt
echo “Following is the uptime information:” >> sysFile.txt
uptime >> sysFile.txt >> sysFile.txt
echo >> sysFile.txt

#Showing free memory >> sysFile.txt
echo “Memory Details:” >> sysFile.txt
free >> sysFile.txt >> sysFile.txt
echo >> sysFile.txt

#Disk space usage using df command >> sysFile.txt
echo “Disk Space Utilization:” >> sysFile.txt
df -mh >> sysFile.txt
echo  >> sysFile.txt
