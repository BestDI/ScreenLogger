### ScreenLogger -- 一个简单可用的屏幕日志插件


[![](https://jitpack.io/v/BestDI/ScreenLogger.svg)](https://jitpack.io/#BestDI/ScreenLogger)
------

#### How to Use it?
1.Add it in your root build.gradle at the end of repositories:
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

2.init in your application:
```kotlin
override fun onCreate() {
    super.onCreate()

    ProcessLifecycleOwner.get().lifecycle.apply {
        addObserver(LoggerLifecycleObserver(this@MainApplication, true))
    }
}
```

now, you could use it:
```kotlin
Logger.warn("TAG", "hello from button")
```
