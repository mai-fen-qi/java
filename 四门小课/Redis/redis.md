redis  

#### hash   （对field进行数值计算）应用场景：点赞、收藏、详情页（）



set  sean：：name   ‘kj’     存储成功   键为 sean：：name   值为kj

get sean：：name                    返回 值 kj  （sean：：name 是一个完整的键）

set  sean：：age 18



keys  sean*  结果      返回sean 开始的所有键    “sean：：name”        “sean：：age ”



#### (存储一个)

hset  sean name   kj                         存储sean 的name为kj                              

#### (存储多个）

hmset sean age  18   address bj    存储sean 的age为18，  address 为bj     

#### （获取一个）

hget sean name   **返回结果**   kj           

#### (获取多个)

  hmget  sean name age        **返回结果**  kj 18

#### （获取所有键）

hkeys sean     **返回结果** name age address

#### （获取所有值）

hvals sean  **返回结果**  kj 18 bj

#### （获取所有键值对）

HGETALL sean   **返回结果** name kj  age 18  address bj



#### （对hash数值进行操作）

HINCRBYFLOAT  sean age 0.5     **返回结果**  18.5  （增加一个浮点数）

hget     sean   age           **返回结果**   18.5

HINCRBYFLOAT sean age -1   **返回结果** 17.5   **支持负数操作**



 





#### SET  （去重集合）无序、去重

SMEMBERS   命令 (会消耗网卡吞吐量)

sadd     k1    tom sean peter ooxx  tom xxoo  **返回结果**   5                             新增五条信息

SMMBERS   k1     **返回结果**  sean tom ooxx petere  xxoo  （把重复的tom 去掉了）

srem  k1  ooxx  xxoo    **返回结果** 2    删除两条信息

SMEMBERS  k1   **返回结果**     tom peter sean    删除了ooxx  和xxoo两条信息

 

#### 交 并 差 集

sadd k1 4 5

sadd  k2  1 2 3  4 5      sadd    k3  4 5 6 7 8 

#### 交集

SINTER  k1 k2   k3  做交集  **返回   **  4 5



#### 返回结果存储到新的键里边（可以减少IO）

SINTERSTORE   dest  k2  k3     **返回结果 **  把 k2 k3的交集 存储到dest 里边

 SMEMBERS dest   **返回结果 **   4  5 





#### 并集

SUNION  k2  k3  **返回结果 **     1 2 3 4 5 6 7 8   （返回结果也回去重）

SUNIONSTORE    带有store  同样可以把返回的结果集放到 新的键值里边



#### 差集（有方向性）

SDIFF k2  k3    **返回结果**  1 2 3   （第一个键值 为主    ）

SDIFF k3  k2    **返回结果**  6 7 8    （第一个键值 为主    ）





#### 产生随机事件

sadd   k1   tom1   tom2   tom3    tom4  tom5  tom6  tom7 

**SRANDMEMBER  key  count  **

**count 是正数 ，取出有一个去重的结果集，数量为count，不能超过已有集合**

**count 是负数，取出一个带重复的结果集，一定满足数量**

**count为0 不返回 **

**解决问题** ： 抽奖（10个奖品 ，用户数量：>10 或<10 ， 	中奖：是否重复 ）

情况 ：多少种组合方式  人多还是奖品多 重复还是不允许重复





假 value 存储的是 人

SRANDMEMBER  k1 3   **返回结果**    tom xoox oxox

SRANDMEMBER  k1 3   **返回结果**    ooxx tom oxox

SRANDMEMBER  k1 3   **返回结果**    ooxx tom xoox    三个结果一定不重复

但是 

SRANDMEMBER  k1 -3   **返回结果**    tom xoox oxox

SRANDMEMBER  k1 3   **返回结果**    tom oxox   xoox 

SRANDMEMBER  k1 3   **返回结果**    tom oxox   tom   就允许出现一样的 （谢谢惠顾）



#### (往外取出值,取出一个）

SPOP  k1    **返回结果** oxox 

SPOP  k1    **返回结果** ooxx

SPOP  k1    **返回结果** nil   (所有信息全部弹出，返回空值)

SPOP  k1    **返回结果** nil   (所有信息全部弹出，返回空值)

---



#### sorted_set   zset(去重 对元素排序)（有正反向索引）

底层排序两种方式：字典序 和 数值序  、

#### 一、 ：物理内存：左小右大，不随命令行改变



假设想让 苹果 香蕉 鸭梨 去排序

排序方式： 按名称、按含糖量等 

  8          2         3    分值

苹果    香蕉    橘子                        元素

 0         1             2

-3         -2            -1                        索引





zadd k1 8 apple  2 banna 3 orange

（增加命令   k1是健  8是分值 apple 是元素 ） 

ZRWANGE  k1 0 -1 **返回结果**  banana   orange   apple    （ 按照  分值进行排序）

ZRANGE k1 0 -1 withscores   **返回结果** banana 2   orange 3  apple  8  (按索引返回数据  带着分值)

ZRANGEBYSCORE k1 3 8 **返回结果** orange  apple  （按数值返回结果 ）

ZRANGE  k1  0  1  **返回结果**  banana  orange  (正向索引  获取数据)

Zrevrange   k1  0  1 **返回结果**  apple  orange          (反向按索引 获取数据）

ZINCRBY  k1 2.5  banana   **返回结果** 4.5   （banana的 分值增加2.5  成为4.5 ）



---

#### 二、有集合操作  （并集，交集）

zadd  k1  80 tom 60 sean  70 baby

zadd  k2  60 tom  100 sean  40 yiming

#### ZUNIONSTORE  unkey          2                k1 	            k2

####                                                    目标key    几个key         第一个key  第二个key      权重     聚合指令(默认是sun 相加操作)

ZRANGE unkey 0 -1 withscores  **返回结果**            yiming   40      baby  70          tom  140   sean  160

​                                                                                （只有k2 有 ）  （只有k1有）     k1 k2 都有 所以相加                                   

#### 权重操作

ZUNIONSTORE  unkey1  2 k1 k2  weight  1  0.5 

ZRANGE           unkey1  0   -1   withscores   **返回结果**  yiming 20   baby   70     sean  110    tom 110

​                                                                                       **k2  的所有分值 乘 0.5  k1的乘0.1**



#### 并集

ZUNIONSTORE  unkey1  2 k1  k2   aggregate  max  

ZRANGE     unkey1 0 -1 withscores  **返回结果**    yiming   40    baby  70   tom  80  sean  100  （取两个分值最大的那个）

#### 交集（不存在 直接踢掉）

----

#### 三、Zset排序是怎么实现的

（ship list  存储结构  跳跃表 ）（类平衡树）



![image-20210823151745875](https://user-images.githubusercontent.com/67794564/131653712-0b87a0a9-d744-4e5b-9fbf-d7c8646e6407.png)


数据量比较多 平均值相对最优





----

####  redis 进阶使用

  管道 Pipelining

#### 创建 socket连接之后  就可以进行命令行操作

nc localhost  6379  **创建socket 链接**

echo -e  “sdfsdf\nsdfsdf”        **返回结果** sdfsdf    sdfsdf

echo  -e  "set k2 99\nicnr k2\n get k2"    | nc  localhost  6379  

（通过echo 把多条命令压缩到一起  发送到 6379这个端口 ）

**返回结果**    +OK   :100                               $3   100  

（创建k2 成功    incr k2 加1的返回值      $3是100的宽度是3             get k2的时候 返回一百）  

----

#### redis  冷启动      先把一部分信息 打入

---------

#### Redis 消息发布 订阅

**pubsub** 命令

PUBLISH  小团团  hello  推送消息  （小团团  比如是 某主播的 直播间）

SUBSCRIBE  小团团   这时候是接收不到 hello的   只能是监听以后才能收到发布的消息

**(但是这样不能往上翻直播间聊天记录)

![redis历史消息](https://user-images.githubusercontent.com/67794564/131666744-be35c2f1-0234-4c99-a178-6cec57df3989.png)


实时消息： 发布订阅功能  **pub  sub ** (命令)

三天之内 的消息  ;  sorted_set  时间就是 分值   用户就是元素   当插入的时候  就已经自动进行排序了

更早的消息 ： 数据库（数据库才会有全量消息)



#### 如果往里边写数据的话 （第二种架构方案）

实时数据：一个客户端往 redis1 里pub（写数据） 一个客户端往 redis1 里sub（取数据）

三天内聊天记录：redis2 往redis1  里边 sub（取数据）然后写到redis2  的 zset 里面  存储三天的聊天记录

更久的聊天记录;一个service 往 redis1 里边 sub（取数据） 推到kafuka里边 ，kafuka 通过DBService 往数据库写。



![redis 消息处理](https://user-images.githubusercontent.com/67794564/131653942-ea256d31-1a0b-483f-976f-ba01b103b0ab.png)

#### 注意：socket连接成本并不很大，因为  不需要密集的cpu计算，而是一个内存存储层，消息到了，直接推送出去。

----

#### Redis 事务

因为redis 是单进程            所以  redis的事务就相当于 队列执行

![redis  事务](https://user-images.githubusercontent.com/67794564/131654009-cbf659c4-f78e-47f4-8c16-6d878163a0b2.png)


#### 因为  进程2 先执行 exec  所以 先把  进程2的所有操作执行完成  

#### （进程2 执行过程中 进程1的所有操作阻塞 ）




![redis 事务  实际执行](https://user-images.githubusercontent.com/67794564/131654060-fe88ac3e-8c16-40aa-ac7c-15ad05246d2c.png)

这时候 进程1 在获取 k1的时候  就会报错（因为 进程2 已经把 k1 删了）



#### 事务监测指令 watch k1（键）

watch  k1 （监控k1）

MULTI （开启事务）

如果 发现 k1发生改变，就不执行 改事务

#### redis 为什么不支持回滚（所以要在业务层面 控制事务）

redis命令只会因为错误的语法而失败，或是命令用在了错误类型的健上面

------

#### redis 布隆过滤器（处理缓存穿透）

在github 下载bloom 插件

下载bloom过滤器
![下载布隆](https://user-images.githubusercontent.com/67794564/131654126-f6da9db2-1f83-47d2-9288-bfbf909bc996.png)


因为是zip 文件  所以还需要下载一个unzip  才能解压zip文件

![布隆zip](https://user-images.githubusercontent.com/67794564/131654145-0db77461-821d-42c6-8092-abdb0ec2983a.png)


然后 直接解压就行  unzip master.zip

![解压](https://user-images.githubusercontent.com/67794564/131654169-8f6866b3-7160-49ba-b7a9-9bcc01149324.png)


进入Makefile 文件目录  直接执行make（需要安装gcc）

然后把redisbloom.so 文件考到redis 目录下
![redis 布隆启动](https://user-images.githubusercontent.com/67794564/131654259-e6cbbba0-b3d5-4214-af60-b7d4d455082e.png)




启动redis 服务                                             挂载一个新的库                                  加载配置文件（不加会有默认的）   



#### 布隆过滤器需要知道的信息：

   1.先看你有什么

   2.有的向bitmap中标记

   3.请求可能会被误标记 

4. 但是大概率会大量减少放行（不可能百分之百阻拦，但是可以降低到百分之一）：减少穿透  

5.  成本低

   ![布隆过滤器的冲突 ](https://user-images.githubusercontent.com/67794564/131654317-1b3bef0e-94b0-4a36-8d9a-f7af1ab1b31b.png)


（元素1 的函数映射 到 bitmap上 **对应的位图 从0变成1 **）

（元素2的函数映射 到bitmap上  **有可能会跟元素1得的映射重叠**）

这时候 用户请求发出   搜的东西刚好有就可以 **找到bitmap中的1** ，返回redis中的值  ，如果 redis没有，就放行到关系型数据库。

​         如果搜的东西刚好没有，就有一定几率，也放行到关系型数据库 ，所以说布隆过滤器是有一定概率的

​     **原因**   1. 一个请求 里边 三条指令  两条没有 一条有  所以会返回个1   但是请求打到数据库 是查不到信息的) 

​                2.三条指令 刚好**元素1** +**元素2 ** 组合到一起 能映射到 ，但是打到数据库，会查询失败。 



微服务架构 一般怎么搭建

**放在 redis中 更合理 因为redis 是内存级的   这样客户端更轻盈 更符合微服务架构**
![redis 布隆 的架构](https://user-images.githubusercontent.com/67794564/131654405-2e2f68c8-bb94-46e2-9521-a5b12e34d484.png)




#### 布隆过滤器的使用

 ![布隆适用](https://user-images.githubusercontent.com/67794564/131654377-37fb2eae-8024-472e-8fad-455223bf46d6.png)

如果有这个数据 就返回1  没有就返回0

**扩充** ：（bloom）布隆过滤器   （counting bloom）布隆过滤器升级版   （Cuckoo）布谷鸟过滤器





----

#### Redis 作为缓存

redis缓存和数据库有什么区别？

缓存级别的话  1. 数据不那么重要     2.  不是全量数据    3.  缓存应该随着访问变化(热数据)(所以要设置key的有效期）





**Redis   key的过期情况：** 

#### 1. 业务逻辑方向（key的有效期）  

   

**设置倒计时过期时间** （1.不会随着访问时间延长）（2. 更改键值 ，会踢除过期时间）

​     **EXPIRE  k1  50   设置  k1 的过期时间为50s**

​      **ttl k1**  查看k1 的 过期时间 

#### 设置定时 过期时间  

​    EXPIREAT k1   1293840000        1293840000s后过期

#### 过期原理    1.被动访问时判断  2.周期轮训判定（增量）（稍微牺牲下内存，但是保住了redis的性能）

  两种方式：被动和主动方式

**主动方式**：  每10s做的事情：   

1. 测试随机的20个keys进行过期检测 
2. 删除所有过期key 

 3.如果有多于25%的key过期，重复步骤1



#### 2.业务运转（内存有限,随着访问变化，应该淘汰掉冷数据）（redis 内存一般控制在1G-10G  配置项的名字：maxmemory-policy noeviction）

#### 回收策略：  

  **LRU** 多久没碰   **LFU** 碰了多少次

1. noeviction  报错（默认）  (做数据库才用)

   2.alikeys-lru 尝试回收最少使用的键（所有的key）（LRU ）  

   3.volatile-lru  尝试回收最少使用的键（快要过期的key）（LRU）

4. alikeys-random 随即回收键                                 （不用因为太随意）
5.  volatile-random  随即回收 仅在过期集合的键   （不用因为太随意）
6. volatile-ttl 回收在过期集合的键，并且优先回收存活时间（TTL）较短的键     （不用，因为成本太高）

----

#### Redis作为数据库

**只要是作为存储层，都有俩东西 **： 1.快照/副本 （RDB）   2.日志  (AOF)

---

#### RDB （时点性）每隔一段时间就会保存一次

操作： 第一种：会阻塞redis  停止对外服务，将数据落地。 第二种：非阻塞，继续对外提供服务。

非阻塞流程    先创建个子进程  ，然后子进程存有父进程所有的值，子进程 把数据写到磁盘  这时候就算父进程的数据发生修改 也不会影响子进程的数据 


![非阻塞](https://user-images.githubusercontent.com/67794564/131654526-b00a2210-c2e0-41e7-a8a6-a319ee116607.png)



第二种需要用到管道  

#### 管道

1. 衔接，前一个命令的输出作为后一个命令的输入
2. 管道会触发 ，创建【子进程】

![管道](https://user-images.githubusercontent.com/67794564/131654566-23fe7eed-4667-467f-9e7f-7fcb11104fb5.png)



先创建一个变量num  ，num=0；（（num++））这时候num=1   ，管道操作 num还是一





#### ./test.sh  &  加一个& 可以让进程在后边执行 不会覆盖用户交互的前台 （在前台的话  就不能干别的）

----

使用LInux的时候：父子进程

父进程的数据，子进程可不可以看得到？

常规思想，数据是数据隔离的！

进阶思想，父进程可以让子进程看到数据！

Linux 中 export 的环境变量，子进程的修改不会破坏父进程，父进程的修改也不会破坏子进程。

-----

#### fork



![fork](https://user-images.githubusercontent.com/67794564/131654588-0d89713e-61eb-46d1-a18f-92d95b6acefc.jpg)


fork 就是复制一份 指针    并不会 在内存开辟两份   只有一份数据

####  copy on write（写时复制） 创建子进程的时候 并不发生复制

好处： 创建进程变快了，  不可能子进程 或者是父进程 都把数据改一遍 ，只改变对应的一个进程 指针引用



----

#### RDB 触发方式：

手动触发 ：1.  save命令   （前端阻塞） 关机维护的时候才会使用   

​                    2.bgsave           （后端异步的 非阻塞）触发fork()子进程

第三种： 配置文件中给出bgsave的规则： save这个标识  ： 

**三个判断阶段**  （60s 内 发生1万次操作进入rdb) ( 300s 内 发生10次操作) （900s 内发生 1次操作） **逐级进行判断**

![rdb 配置文件](https://user-images.githubusercontent.com/67794564/131654643-4eccb554-16fa-4e39-bd75-1cbc0292b11a.png)


save “”  或者删掉其他save配置   就会不会触发RDB操作





---

redis   默认会开启rdb

dbfilename dump.rdb        **持久化rdb文件名称**

#### RDB 弊端 （不支持拉链，永远只有一个dump.rdb）

容易丢失 时点之间的数据  （8点一次rdb  9点一次rdb  可能8-9点之间的数据就丢失了）

#### RDB 优点 类似于java 中的序列化 恢复速度相对较快

----

#### AOF弊端       慢（追加每笔写操作） 体量无线变大 导致恢复慢

#### AOF 优点     丢失数据少  

redis中 ，rdb和aof可以同时开启 ，但如果开启了aof 只会用aof恢复数据 

 redsi 4.0以后  AOF 包含RDB全量，增加记录新的写操作

#### Redis  优化aof 

4.0以前    计量重写  （1. 删除抵消的命令）  （2.  合并重复的命令）(  3.  最终也是一个纯指令的日志文件)

4.0以后    计量重写     （将老的数据RDB到aof文件中）（将增量的以指令方式Append到AOF） 

​                                     （AOF是一个混合体 利用了RDB的快 利用了日志的全量）



-**aof默认是关闭的,需要手动打开**  改为:    appendonly  yes 

Redis是内存数据库 --》 写操作会触发IO--》三种写IO级别（NO、always、everysec）

 NO       就是 操做系统内核先把数据写到buffer里面  当buffer满了之后 才会写到磁盘里    

​              如果一个文件20k  缓存区15k  那剩下的5k就会丢失  所以close()之前  需要手动flush() 一下，

​              强制把缓存区的数据 写到磁盘中

​             

缺点：   有可能会损失一个buffer的数据


![Redis IO 级别 NO](https://user-images.githubusercontent.com/67794564/131847173-50657a4f-745f-4ced-a001-7d97556f90bb.png)



 everysec(每秒的意思） 默认级别   

 always    每次写入buffer的时候  立即调用flush()  这是数据最可靠的方式  最多损失一条数据  








