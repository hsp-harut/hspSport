if [ $# -lt 1 ]; then
  echo 1>&2 "$0: not enough arguments, provide the system name"
  exit 2
fi

mvn clean package -Dmaven.test.skip=true
echo "Deploying $1"
ansible-playbook deploy.yml --extra-vars="system=$1"