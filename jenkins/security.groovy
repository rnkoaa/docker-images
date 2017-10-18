#!groovy
 
import jenkins.model.*
import hudson.security.*
import jenkins.security.s2m.AdminWhitelistRule
 
def instance = Jenkins.getInstance()
 
def user = 'admin', pass = 'admin'

// first try to see if the jenkins username was mounted as a docker secret
// if not, try to see if it was passed in as an environment variable
// Lastly the least safe is to use the hard coded variable
def jenkinsUserFile = new File("/run/secrets/jenkins-user")
if(jenkinsUserFile.exists())
	user = jenkinsUserFile.text().trim()
else {
	// try to get it from environment variables
	def adminUser = System.getenv("JENKINS_ADMIN_USER")
	if(adminUser != null && adminUser.size() > 0)
		user = adminUser
}

// first try to see if the jenkins password was mounted as a docker secret
// if not, try to see if it was passed in as an environment variable
// Lastly the least safe is to use the hard coded variable
def jenkinsPassFile = new File("/run/secrets/jenkins-pass")
if(jenkinsPassFile.exists())
 	pass = jenkinsPassFile.text().trim()
else {
 	// try to get it from environment variables
 	def adminPass = System.getenv("JENKINS_ADMIN_PASSWORD")
 	if(adminPass != null && adminPass.size() > 0)
 		pass = adminPass
 }

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount(user, pass)
instance.setSecurityRealm(hudsonRealm)
 
def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)
instance.save()
 
Jenkins.instance.getInjector().getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)