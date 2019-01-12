workflow "TestFlow" {
  on = "push"
  resolves = ["Test on TravisCI"]
}

action "Test on TravisCI" {
  uses = "travis-ci/actions@master"
}
