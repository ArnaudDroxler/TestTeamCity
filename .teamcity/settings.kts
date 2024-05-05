import jetbrains.buildServer.configs.kotlin.*

project {
    buildType(ChainConfigA)
    buildType(ChainConfigB)
    buildType(ChainConfigC)
}

object ChainConfigA : BuildType({
    name = "ChainConfigA"

    params {
        param("chain.ConfigA.param", "Config A")
    }

    steps {
        script {
            scriptContent = """echo "Parameter value is: %chain.ConfigA.param%""""
        }
    }
})

object ChainConfigB : BuildType({
    name = "ChainConfigB"

    params {
        param("chain.ConfigB.param", "Config B")
    }

    steps {
        script {
            scriptContent = """echo "Parameter value is: %chain.ConfigB.param%""""
        }
    }

    dependencies {
        snapshot(ChainConfigA) {
            reuseBuilds = ReuseBuilds.NO
        }
    }
})

object ChainConfigC : BuildType({
    name = "ChainConfigC"

    params {
        param("chain.ConfigC.param", "Config C")
        param("reverse.dep.ChainConfigA.chain.ConfigA.param", "Value Overridden in ConfigC")
    }

    steps {
        script {
            scriptContent = """echo "Parameter value is: %chain.ConfigC.param%""""
        }
    }

    dependencies {
        snapshot(ChainConfigB) {
            reuseBuilds = ReuseBuilds.NO
        }
    }
})