# Android 面试题完整版

## 1、四大组件是什么？简单介绍

**是什么：**
- **Activity**：用户界面组件，负责与用户交互
- **Service**：后台服务组件，执行长时间运行的操作
- **BroadcastReceiver**：广播接收器，接收系统或应用发出的广播消息
- **ContentProvider**：内容提供者，管理应用间数据共享

**为什么需要：** Android 采用组件化架构，将不同职责分离，便于系统管理生命周期和资源

**好处：** 解耦、复用性强、系统可统一调度和优化资源

---

## 2、Activity 的启动模式？路由相关（关注 4 种模式和 7 种 Flag）

**4 种启动模式：**
- **standard**（标准模式）：每次启动都创建新实例，可多次存在于栈中
- **singleTop**（栈顶复用）：如果目标 Activity 已在栈顶，则复用并调用 `onNewIntent()`，否则创建新实例
- **singleTask**（栈内复用）：整个任务栈中只有一个实例，启动时会清除其上所有 Activity
- **singleInstance**：独占一个任务栈，全局唯一实例

**常用 Flag：**
- `FLAG_ACTIVITY_NEW_TASK`：在新任务栈中启动
- `FLAG_ACTIVITY_CLEAR_TOP`：清除目标 Activity 之上的所有 Activity
- `FLAG_ACTIVITY_SINGLE_TOP`：等同于 singleTop 模式
- `FLAG_ACTIVITY_CLEAR_TASK`：清空任务栈后启动
- `FLAG_ACTIVITY_NO_HISTORY`：Activity 不保留在栈中
- `FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS`：不出现在最近任务列表
- `FLAG_ACTIVITY_BROUGHT_TO_FRONT`：系统自动添加，将 Activity 带到前台

**为什么需要：** 控制 Activity 实例数量和任务栈结构，避免重复创建、优化内存

**好处：** 提升用户体验（如登录页不重复创建）、节省资源

---

## 3、屏幕旋转的生命周期？

**流程：**
1. 配置改变时，Activity 默认会销毁重建
2. 生命周期：`onPause()` → `onStop()` → `onDestroy()` → `onCreate()` → `onStart()` → `onResume()`
3. 在 `onSaveInstanceState()` 中保存状态，在 `onCreate()` 或 `onRestoreInstanceState()` 中恢复

**为什么会销毁：** 屏幕旋转属于配置变更��Configuration Change），系统需要重新加载适配横竖屏的资源（布局、图片等）

**如何避免重建：**
- 在 `AndroidManifest.xml` 中配置 `android:configChanges="orientation|screenSize"`，Activity 不会重建，而是回调 `onConfigurationChanged()`

**好处：** 自动适配不同屏幕方向的 UI；手动处理可避免数据丢失和性能损耗

---

## 4、如何启动 Service？

**两种启动方式：**

**1. startService()**
- 调用 `startService(Intent)`，Service 执行 `onStartCommand()`
- Service 独立运行，即使启动者销毁也继续执行
- 需手动调用 `stopService()` 或 `stopSelf()` 停止

**2. bindService()**
- 调用 `bindService(Intent, ServiceConnection, flags)`，Service 执行 `onBind()`
- 返回 IBinder 对象，客户端通过它与 Service 通信
- 所有客户端解绑后，Service 自动销毁

**为什么两种方式：** startService 适合后台任务（如下载），bindService 适合需要交互的场景（如音乐播放器控制）

**好处：** 灵活控制 Service 生命周期和通信方式

---

## 5、Android 实现线程间通信的几种方式？

**1. Handler + Looper + MessageQueue**
- 子线程通过 Handler 发送 Message 到主线程的 MessageQueue，Looper 轮询处理
- 用途：子线程更新 UI

**2. AsyncTask（已废弃）**
- 封装了线程池和 Handler，简化异步任务
- 缺点：容易内存泄漏，已被 Kotlin 协程替代

**3. Binder（跨进程通信 IPC）**
- 基于 C/S 架构，通过内核驱动实现进程间数据传递
- 用途：AIDL、四大组件通信

**4. BroadcastReceiver**
- 通过系统广播机制传递消息
- 用途：跨组件、跨应用通信

**5. EventBus / LiveData**
- 事件总线，解耦发送者和接收者
- LiveData 具有生命周期感知，避免内存泄漏

**为什么需要：** Android 主线程不能执行耗时操作，需要线程间协作

**好处：** 保证 UI 流畅性，避免 ANR（Application Not Responding）

---

## 6、广播有哪几种类型？举例说明

**1. 标准广播（Normal Broadcast）**
- 异步执行，所有接收者几乎同时收到
- 示例：`sendBroadcast(Intent)`，如网络状态变化

**2. 有序广播（Ordered Broadcast）**
- 按优先级依次传递，高优先级可拦截或修改数据
- 示例：`sendOrderedBroadcast(Intent)`，如短信拦截

**3. 本地广播（Local Broadcast）**
- 仅在应用内传播，使用 `LocalBroadcastManager`
- 示例：应用内模块间通信

**4. 粘性广播（Sticky Broadcast，已废弃）**
- 广播会保留，新注册的接收者也能收到
- 已被 EventBus 或 LiveData 替代

**为什么需要：** 实现组件间松耦合通信

**好处：** 灵活、解耦，支持系统级和应用级事件分发

---

## 7、Looper 的理解

**是什么：**
- Looper 是消息循环器，不断从 MessageQueue 中取出 Message 并分发给对应的 Handler 处理

**工作流程：**
1. `Looper.prepare()` 创建 Looper 和 MessageQueue，绑定到当前线程
2. `Looper.loop()` 开启无限循环，调用 `MessageQueue.next()` 取消息
3. Handler 发送的 Message 进入 MessageQueue，Looper 取出后回调 `Handler.handleMessage()`

**为什么需要：** 线程默认执行完就结束，Looper 让线程保持活跃，持续处理消息

**好处：** 实现异步消息机制，主线程的 Looper 由系统自动创建

---

## 8、SharedPreferences 的工作原理

**是什么：** 轻量级键值对存储，基于 XML 文件

**工作流程：**
1. 首次访问时，从 `/data/data/包名/shared_prefs/` 读取 XML 文件到内存
2. 读操作直接从内存获取
3. 写操作通过 `Editor.commit()`（同步）或 `apply()`（异步）写入文件

**为什么用它：** 适合存储简单配置（如用户设置、登录状态）

**好处：** 简单易用，自动处理文件 I/O

**缺点：** 不适合大数据量，首次加载会阻塞线程；多进程不安全

---

## 9、RecyclerView 的渲染机制和 item 的复用策略？

**渲染机制：**
1. LayoutManager 负责布局（LinearLayoutManager、GridLayoutManager 等）
2. Adapter 提供数据和 ViewHolder
3. ItemAnimator 处理动画

**复用策略（四级缓存）：**
1. **Scrap 缓存**：屏幕内刚移除的 ViewHolder，直接复用无需重新绑定
2. **Cache 缓存**：默认缓存 2 个刚滑出屏幕的 ViewHolder，按位置匹配
3. **ViewCacheExtension**：自定义缓存（很少使用）
4. **RecycledViewPool**：按 ViewType 缓存，需要重新绑定数据

**为什么需要：** 避免频繁创建 View，优化滑动性能

**好处：** 高效复用，减少内存抖动和 GC 压力

---

## 10、Service 在 Android 中的作用和用法

**作用：** 执行后台长时间运行的操作，无 UI 界面

**用法：**
- **前台 Service**：显示通知，优先级高，不易被杀死（如音乐播放）
- **后台 Service**：Android 8.0+ 受限，需用 WorkManager 或 JobScheduler 替代
- **绑定 Service**：提供客户端-服务器接口，支持 IPC

**为什么需要：** Activity 不适合执行耗时任务，Service 生命周期独立

**好处：** 保证任务持续执行，不受 UI 生命周期影响

---

## 11、Java 中的内存回收机制和对 GC Root 的理解

**GC 机制：**
- **可达性分析**：从 GC Roots 出发，标记所有可达对象，不可达对象被回收
- **分代回收**：新生代（Eden + Survivor）、老年代，不同代使用不同算法

**GC Roots 包括：**
1. 虚拟机栈中引用的对象（局部变量）
2. 方法区中静态属性引用的对象
3. 方法区中常量引用的对象
4. Native 方法栈中引用的对象
5. 活跃线程

**为什么需要 GC Roots：** 作为起点判断对象是否存活

**好处：** 自动回收内存，避免内存泄漏

---

## 12、进程调度的算法

**常见算法：**
1. **先来先服务（FCFS）**：按到达顺序执行，简单但可能导致长作业等待
2. **短作业优先（SJF）**：优先执行短任务，平均等待时间短
3. **时间片轮转（RR）**：每个进程执行固定时间片，公平但上下文切换开销大
4. **优先级调度**：高优先级先执行，可能导致饥饿
5. **多级反馈队列**：结合时间片和优先级，动态调整

**Android 中的应用：**
- 前台进程优先级最高，后台进程可能被杀死
- 使用 CFS（完全公平调度器）

**为什么需要：** 合理分配 CPU 资源，提升系统响应速度

**好处：** 平衡吞吐量和响应时间

---

## 13、如何自定义一个 View，请谈谈其中 Canvas 的作用

**自定义 View 步骤：**
1. 继承 `View` 或 `ViewGroup`
2. 重写 `onMeasure()`：测量尺寸
3. 重写 `onLayout()`（ViewGroup）：布局子 View
4. 重写 `onDraw(Canvas)`：绘制内容

**Canvas 的作用：**
- 画布，提供绘制 API：`drawRect()`、`drawCircle()`、`drawText()` 等
- 配合 `Paint` 设置颜色、样式、抗锯齿等

**为什么需要：** 系统控件无法满足复杂 UI 需求

**好处：** 灵活定制，实现特殊效果（如仪表盘、波浪动画）

---

## 14、Android 中的网络请求？

**常用方式：**
1. **HttpURLConnection**：系统原生，轻量但 API 繁琐
2. **OkHttp**：高性能 HTTP 客户端，支持连接池、拦截器、缓存
3. **Retrofit**：基于 OkHttp，注解式 API，配合 Gson/Moshi 解析 JSON
4. **Volley**：Google 官方，适合小数据量高频请求

**为什么用 Retrofit + OkHttp：**
- Retrofit 简化接口定义，OkHttp 处理底层连接
- 支持协程、RxJava 等异步方案

**好处：** 代码简洁、易维护、性能优

---

## 15、Handler 的工作原理？

**核心组件：**
- **Handler**：发送和处理消息
- **Message**：消息对象，携带数据
- **MessageQueue**：消息队列，单链表结构
- **Looper**：循环器，不断从队列取消息分发给 Handler

**工作流程：**
1. 子线程调用 `handler.sendMessage(msg)`，Message 进入主线程的 MessageQueue
2. 主线程 Looper 调用 `MessageQueue.next()` 取出消息
3. Looper 调用 `msg.target.dispatchMessage(msg)`，最终执行 `Handler.handleMessage()`

**为什么需要：** 子线程不能直接更新 UI，需通过 Handler 切换到主线程

**好处：** 线程安全，避免 UI 操作冲突

---

## 16、如何在 Android 中进行数据持久化？

**5 种方式：**
1. **SharedPreferences**：键值对，适合简单配置
2. **文件存储**：内部存储（私有）、外部存储（公共）
3. **SQLite 数据库**：结构化数据，支持复杂查询
4. **Room**：Google 官方 ORM，基于 SQLite，编译时检查 SQL
5. **DataStore**：替代 SharedPreferences，支持协程和类型安全

**为什么需要：** 应用关闭后数据不丢失

**好处：** 根据场景选择合适方案，平衡性能和复杂度

---

## 17、Android 的 BroadcastReceiver 的作用

**作用：** 接收系统或应用发出的广播消息

**使用场景：**
- 监听系统事件（如开机、网络变化、电量低）
- 应用内组件通信
- 跨应用通信（需权限）

**注册方式：**
1. **静态注册**：在 `AndroidManifest.xml` 中声明，应用未启动也能接收（Android 8.0+ 受限）
2. **动态注册**：代码中调用 `registerReceiver()`，灵活但需手动注销

**为什么需要：** 实现事件驱动架构，解耦发送者和接收者

**好处：** 灵活、松耦合

---

## 18、Android 中实现动画效果

**3 种动画：**
1. **View 动画（补间动画）**：
   - 平移、缩放、旋转、透明度
   - 只改变视觉效果，不改变实际位置
   - 示例：`TranslateAnimation`、`AlphaAnimation`

2. **属性动画（Property Animation）**：
   - 真正改变对象属性（如 `translationX`、`alpha`）
   - 示例：`ObjectAnimator.ofFloat(view, "translationX", 0f, 100f)`
   - 支持自定义属性和插值器

3. **帧动画（Drawable Animation）**：
   - 逐帧播放图片序列
   - 示例：`AnimationDrawable`

**为什么用属性动画：** View 动画只是"障眼法"，点击区域不变；属性动画真正改变属性

**好处：** 提升用户体验，实现流畅过渡效果

---

## 19、如何在 Android 中实现多线程

**5 种方式：**
1. **Thread + Runnable**：原生 Java 线程
2. **Handler + HandlerThread**：带 Looper 的线程，适合串行任务
3. **AsyncTask**（已废弃）：简化异步任务，但易内存泄漏
4. **线程池（Executor）**：复用线程，避免频繁创建销毁
   - `Executors.newFixedThreadPool()`、`newCachedThreadPool()` 等
5. **Kotlin 协程**：轻量级线程，结构化并发，推荐方案

**为什么需要：** 主线程不能执行耗时操作（网络、数据库、文件 I/O）

**好处：** 避免 ANR，提升响应速度

---

## 20、Android 的 Parcelable 和 Serializable 的区别

| 特性 | Parcelable | Serializable |
|------|-----------|--------------|
| **实现方式** | Android 专用，需手动实现 `writeToParcel()` 和 `CREATOR` | Java 标准，实现接口即可 |
| **性能** | 高（内存序列化，无反射） | 低（磁盘序列化，大量反射） |
| **使用场景** | Intent 传递对象、Binder IPC | 持久化存储、网络传输 |
| **代码量** | 多（可用插件生成） | 少 |

**为什么 Parcelable 更快：** 直接操作内存，避免反射和 I/O

**好处：** 根据场景选择，Intent 传递用 Parcelable，持久化用 Serializable

---

## 21、Android 中如何处理权限

**权限分类：**
1. **普通权限**：自动授予（如网络访问）
2. **危险权限**：需运行时申请（如相机、定位、存储）

**处理流程（Android 6.0+）：**
1. 在 `AndroidManifest.xml` 中声明权限
2. 运行时检查：`ContextCompat.checkSelfPermission()`
3. 请求权限：`ActivityCompat.requestPermissions()`
4. 处理结果：重写 `onRequestPermissionsResult()`

**推荐方案：**
- 使用 `ActivityResultContracts.RequestPermission()`（Jetpack）
- 第三方库：PermissionsDispatcher、EasyPermissions

**为什么需要：** 保护用户隐私，符合 Google 政策

**好处：** 提升用户信任，避免应用被下架

---

## 22、Android 中 View 的工作原理

**三大流程：**
1. **Measure（测量）**：
   - 从根 View 递归调用 `measure()`，确定每个 View 的宽高
   - `MeasureSpec` 封装父 View 对子 View 的尺寸要求（EXACTLY、AT_MOST、UNSPECIFIED）

2. **Layout（布局）**：
   - 从根 View 递归调用 `layout()`，确定每个 View 的位置（left、top、right、bottom）

3. **Draw（绘制）**：
   - 从根 View 递归调用 `draw()`，绘制背景、内容、子 View、装饰

**为什么需要三步：** 分离关注点，先确定尺寸，再确定位置，最后绘制

**好处：** 灵活、可扩展，支持自定义 View

---

## 23、Android 中如何实现数据绑定？

**DataBinding：**
- Jetpack 组件，在 XML 中直接绑定数据
- 示例：
  ```xml
  <TextView
      android:text="@{user.name}" />
  ```
- 双向绑定：`@={viewModel.text}`

**ViewBinding：**
- 轻量级方案，自动生成 View 引用类
- 替代 `findViewById()`，类型安全

**为什么需要：** 减少模板代码，避免空指针

**好处：** 代码简洁、编译时检查、性能优

---

## 24、Android 中的 Jetpack 组件，它们的作用是什么？

**核心组件：**
1. **Lifecycle**：生命周期感知，避免内存泄漏
2. **LiveData**：可观察数据，自动更新 UI
3. **ViewModel**：存储 UI 数据，配置变更时保留
4. **Room**：SQLite ORM，编译时检查 SQL
5. **Navigation**：管理 Fragment 导航
6. **WorkManager**：后台任务调度，替代 Service
7. **Paging**：分页加载大数据集
8. **DataStore**：替代 SharedPreferences
9. **Hilt**：依赖注入，基于 Dagger

**为什么需要：** 统一架构，解决常见问题（生命周期、数据持久化、导航）

**好处：** 减少样板代码，提升开发效率，官方支持

---

## 25、Android 中的 MVVM 架构模式

**组成部分：**
- **Model**：数据层（Repository、数据库、网络）
- **View**：UI 层（Activity、Fragment）
- **ViewModel**：业务逻辑层，持有 LiveData，不持有 View 引用

**数据流：**
1. View 观察 ViewModel 的 LiveData
2. 用户操作触发 ViewModel 方法
3. ViewModel 调用 Repository 获取数据
4. LiveData 更新，View 自动刷新

**为什么用 MVVM：** 解耦 UI 和业务逻辑，便于测试

**好处：** 代码清晰、可维护性强、配合 Jetpack 使用体验佳

---

## 26、Android 中的内存泄漏常见场景及解决方案

**常见场景：**
1. **非静态内部类持有外部类引用**：
   - 问题：Handler、AsyncTask 持有 Activity 引用
   - 解决：使用静态内部类 + 弱引用

2. **单例持有 Context**：
   - 问题：单例生命周期长，持有 Activity 导致无法回收
   - 解决：使用 `ApplicationContext`

3. **资源未关闭**：
   - 问题：Cursor、InputStream、BroadcastReceiver 未关闭
   - 解决：在 `onDestroy()` 中释放

4. **属性动画未停止**：
   - 问题：无限循环动画持有 View 引用
   - 解决：在 `onDestroy()` 中调用 `animator.cancel()`

**检测工具：** LeakCanary、Android Profiler

**为什么需要关注：** 内存泄漏导致 OOM（Out Of Memory）

**好处：** 提升应用稳定性和性能

---

## 27、Android 中的 Binder 机制

**是什么：** Android 特有的 IPC（进程间通信）机制，基于 C/S 架构

**核心组件：**
- **Client**：客户端，发起请求
- **Server**：服务端，处理请求
- **ServiceManager**：管理所有 Binder 服务
- **Binder 驱动**：内核层，负责数据传递

**工作流程：**
1. Server 向 ServiceManager 注册服务
2. Client 通过 ServiceManager 获取 Binder 代理
3. Client 调用代理方法，Binder 驱动将数据传递给 Server
4. Server 处理后返回结果

**为什么用 Binder：**
- 性能高：只需一次数据拷贝（传统 IPC 需两次）
- 安全：支持身份验证（UID/PID）

**好处：** 四大组件通信、AIDL 底层实现

---

## 28、Android 中的 ANR 原因及解决方案

**ANR 触发条件：**
1. **主线程阻塞 5 秒**（输入事件无响应）
2. **BroadcastReceiver 10 秒未执行完**
3. **Service 20 秒未响应**

**常见原因：**
- 主线程执行耗时操作（网络、数据库、文件 I/O）
- 死锁或等待其他线程
- CPU 被其他进程占用

**解决方案：**
1. 耗时操作移到子线程（协程、线程池）
2. 使用 `StrictMode` 检测主线程违规操作
3. 优化算法，减少计算量
4. 分析 `/data/anr/traces.txt` 日志

**为什么需要避免：** ANR 导致用户体验差，应用可能被强制关闭

**好处：** 保证应用流畅性

---

## 29、Android 中的 Kotlin 协程

**是什么：** 轻量级线程，用同步方式写异步代码

**核心概念：**
- **CoroutineScope**：协程作用域，管理生命周期
- **suspend 函数**：可挂起函数，不阻塞线程
- **Dispatchers**：调度器（Main、IO、Default）

**示例：**
```kotlin
lifecycleScope.launch {
    val data = withContext(Dispatchers.IO) {
        fetchDataFromNetwork() // 子线程执行
    }
    updateUI(data) // 主线程更新 UI
}
```

**为什么用协程：**
- 避免回调地狱
- 结构化并发，自动取消子协程
- 配合 Jetpack（ViewModel、Room）

**好处：** 代码简洁、易读、性能优

---

## 30、Android 中的屏幕适配方案

**常见方案：**
1. **dp/sp 单位**：系统自动根据屏幕密度缩放
2. **布局适配**：
   - 使用 `ConstraintLayout` 替代固定尺寸
   - 百分比布局、权重（`layout_weight`）
3. **资源适配**：
   - 提供不同分辨率的图片（hdpi、xhdpi、xxhdpi）
   - 不同屏幕尺寸的布局（layout-sw600dp）
4. **今日头条方案**：修改系统 density，统一按设计稿宽度适配
5. **SmallestWidth 适配**：为不同 sw 值提供 dimens 文件

**为什么需要：** Android 设备屏幕尺寸和密度差异大

**好处：** 保证 UI 在不同设备上显示一致
