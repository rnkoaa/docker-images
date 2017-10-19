#!groovy
 
import jenkins.model.*
import hudson.security.*
import java.util.logging.LogManager
import jenkins.security.s2m.AdminWhitelistRule
 

def logger = LogManager.getLogManager().getLogger("")
def instance = Jenkins.getInstance()
def env = System.getenv()
logger.info("RUNNING security.groovy from ${Jenkins.instance.getRootDir().absolutePath}")
def user, pass

logger.info("Here are the Jenkins environment variables on startup")
env.each{
 logger.info("${it}")
}

// first try to see if the jenkins username was mounted as a docker secret
// if not, try to see if it was passed in as an environment variable
// Lastly the least safe is to use the hard coded variable
def jenkinsUserFile = new File("/run/secrets/jenkins-user")
if(jenkinsUserFile.exists())
	user = jenkinsUserFile.text().trim()
else {
	// try to get it from environment variables
	def adminUser =env['JENKINS_ADMIN_USER']
	user = (adminUser) ? adminUser : 'admin'
	 logger.info("Setting Admin User as ${user}")
}

// first try to see if the jenkins password was mounted as a docker secret
// if not, try to see if it was passed in as an environment variable
// Lastly the least safe is to use the hard coded variable
def jenkinsPassFile = new File("/run/secrets/jenkins-pass")
if(jenkinsPassFile.exists())
 	pass = jenkinsPassFile.text().trim()
else {
 	// try to get it from environment variables
 	def adminPass = env['JENKINS_ADMIN_PASSWORD']
	 pass = (adminPass) ? adminPass : 'admin'
	  logger.info("Setting Admin User as ${pass}")
 }

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount(user, pass)
instance.setSecurityRealm(hudsonRealm)
 
def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)
instance.save()
 
Jenkins.instance.getInjector().getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)