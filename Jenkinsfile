#!/usr/bin/env groovy

/*
    This pipeline creates a Maven application and then deploys the
    application to EKS for each stage: alpha, beta, gamma, prod.

    The pipeline will prompt to continue for production deployment. A ServiceNow CHG ticket will be created
    when deploying to production.

    If there is an error or failure at any point in the pipeline, a Slack message will be sent to a specified
    Slack channel.

    Branches:
    Master - builds, uploads artifacts, deploys
    Non-master - builds
 */

pipeline {
    agent none
    environment {
        SERVICE_NAME = JOB_NAME.replaceAll(/-pipeline.*/, '')
        SLACK_CHANNEL = 'some-channel'
        MASTER_BRANCH = 'master'
        OVERRIDE_FILE = 'values.yaml'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
        disableResume()
        parallelsAlwaysFailFast()
        timestamps()
    }
    stages {
        stage('pr pipeline') {
            when { not { branch MASTER_BRANCH } }
            steps {
                egenciaPullRequestBuild(serviceName: SERVICE_NAME)
            }
        }
        stage('master pipeline') {
            when { branch MASTER_BRANCH }
            stages {
                stage('build') {
                    steps {
                        script {
                            env.VERSION = egenciaBuild(
                                    serviceName: SERVICE_NAME,
                                    slackChannel: SLACK_CHANNEL,
                                    uploadAssets: false
                            )
                        }
                    }
                }
                stage('deploy alpha') {
                    steps {
                        egenciaDeploy(
                                serviceName: SERVICE_NAME,
                                stage: 'alpha',
                                substrates: ['us-west-2-lab'],
                                tag: env.VERSION,
                                overrideFile: OVERRIDE_FILE
                        )
                    }
                }
                stage('deploy beta') {
                    steps {
                        egenciaDeploy(
                                serviceName: SERVICE_NAME,
                                stage: 'beta',
                                substrates: ['us-west-2-lab', 'eu-west-1-lab'],
                                tag: env.VERSION,
                                overrideFile: OVERRIDE_FILE
                        )
                        // TODO: Replace labTestUserUsername and labTestUserPassword with the real lab/beta test user's credentials
                        runLighthouseTests('lab', 'labTestUserUsername', 'labTestUserPassword')
                    }
                }
                stage('deploy gamma') {
                    steps {
                        egenciaDeploy(
                                serviceName: SERVICE_NAME,
                                stage: 'gamma',
                                substrates: ['us-west-2-prod'],
                                tag: env.VERSION,
                                overrideFile: OVERRIDE_FILE
                        )
                    }
                }
                stage('deploy to prod?') {
                    steps {
                        slackSend channel: SLACK_CHANNEL, color: 'good', message: "@here <${env.RUN_DISPLAY_URL}|${env.JOB_NAME}:${env.BUILD_NUMBER}> is waiting to deploy to beta :timer_clock:!"
                        timeout(time: 60, unit: 'MINUTES') {  input 'Do you want deploy to prod?' }
                    }
                }
                stage('prod chg') {
                    agent { label 'base' }
                    steps {
                        script {
                            checkout scm
                            env.COMMIT_ID = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                            env.COMMIT_AUTHOR = sh(script: 'git show -s --format="%aE"', returnStdout: true).trim()
                            createChange(
                                    serviceName: SERVICE_NAME,
                                    commitId: env.COMMIT_ID,
                                    commitAuthor: env.COMMIT_AUTHOR,
                                    regions: ['us-west-2', 'eu-west-1'],
                                    environment: 'prod'
                            )
                        }
                    }
                }
                stage('deploy prod') {
                    steps {
                        egenciaDeploy(
                                serviceName: SERVICE_NAME,
                                stage: 'prod',
                                substrates: ['us-west-2-prod', 'eu-west-1-prod'],
                                tag: env.VERSION,
                                overrideFile: OVERRIDE_FILE
                        )
                        // TODO: Replace prodTestUserUsername and prodTestUserPassword with the real prod test user's credentials
                        runLighthouseTests('prod', 'prodTestUserUsername', 'prodTestUserPassword')
                    }
                }
            }
        }
    }
    post {
        success {
            postCompletionSuccess()
        }
        failure {
            postCompletionFailed()
            slackSend channel: "${SLACK_CHANNEL}", color: 'danger', message: "@here [CUMULUS] <${env.RUN_DISPLAY_URL}|${env.JOB_NAME}:${env.BUILD_NUMBER}> pipeline failed :build_failed:"
        }
    }
}

def runLighthouseTests(String lighthouseStage, String username, String password) {
    /*
        Documentation:
        1. Jenkins shared library: https://github.expedia.biz/Egencia/cumulus-jenkins-shared-libraries/blob/master/vars/runGoogleLighthouse.md
        2. Egencia Lighthouse codebase: https://github.expedia.biz/Egencia/lighthouse-performance-audit-tests
    */

    // TODO: Replace pages with the pages in your app
    stage('Run Lighthouse tests') {
        runGoogleLighthouse(
            pages: "page1,page2",
            stage: lighthouseStage,
            device: "desktop",
            configName: "common.lighthouse.audits",
            username: username,
            password: password,
            slackChannel: slackChannel
        )
    }
}
