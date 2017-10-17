.PHONY: infrastructure

infrastructure:
	@docker pull rnkoaa/jenkins:lts
	@docker pull sonatype/nexus3

build-infrastructure:
	@docker-compose -p rnkoaa -f infrastructure-docker-compose.yml up -d

clean-build-infrastructure:
	@docker-compose -p rnkoaa -f infrastructure-docker-compose.yml down