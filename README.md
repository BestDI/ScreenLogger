### ScreenLogger -- 一个简单可用的屏幕日志插件


[![](https://jitpack.io/v/BestDI/ScreenLogger.svg)](https://jitpack.io/#BestDI/ScreenLogger)
------

#### How to Use it?

1. Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add the dependency
```groovy
dependencies {
    implementation 'com.github.BestDI:ScreenLogger:v1.0'
}
```

2. init in your application:
```kotlin
override fun onCreate() {
    super.onCreate()

    ProcessLifecycleOwner.get().lifecycle.apply {
        val isEnable = true // 可以通过isEnable的状态控制是否打开ScreenLog
        addObserver(LoggerLifecycleObserver(this@MainApplication, isEnable))
    }
}
```

now, you could use it:
```kotlin
Logger.warn("TAG", "hello from button")
```

or you could use:
`typealias ScreenLogger = Logger`

#### use result

1.request permission(above android 6.0)

![](shots/permission.png)

2.tracking log

![](shots/track.png)

3.log details

![](shots/logdetails.png)