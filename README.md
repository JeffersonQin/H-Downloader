**重要：本项目基本上已经不在维护，但是欢迎提交PR!**

**IMPORTANT: THIS PROJECT IS NO LONGER IN INTENSIVE MAINTENANCE, BUT PULL REQUESTS ARE WELCOMED**

# 简介

`H-Downloader`是我于2020年2月写的本子下载器（一年半过去了），整理代码时修了点bug并开源。其可以下载如下网站的本子：
- e-hentai.org
- ja.erocool.com

# Known Issues

- 未处理`e-hentai`超过40页，即要翻页（有多个简介页）的情况，看心情写。
- 国内`erocool`的主站被墙，`cdn`可能没有被墙。在`analyze`和`download`之间记得开关代理。
- `erocool`用了`Cloudflare`做`cdn`，所以对于并发比较敏感。在`H_Downloader.java`当中有一个`MAX_CONCURRENT_THREAD`的常量可以配置下载的并发线程数。（这也是维护时新增的功能）

# 启动方法

我只能说，多种多样。有一些边缘的`case`将在我的博客中记录：https://gyrojeff.top/index.php/archives/H-Downloader-当年写的本子下载器/

## `jar`包启动

```bash
java -jar <path-to-jar>
```

# 说明

学习项目，很多地方不太了解，有很多地方写的很不成熟，以及可能把`idea`的配置文件也留在`repo`里了，也不太清楚该不该留🤣。
