 nohup ./standalone.sh -Djboss.bind.address=10.0.2.15 &
 ps -ef |grep java| awk '/jboss-as-7.1/ {print $2}' | xargs kill
