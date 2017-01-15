/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.belong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.VersionResourceResolver;

/**
 @author weikun
  * @since 2016-11-3 21:42
 */
/*
	WebMvcConfigurerAdapter:可重写@Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login").setViewName("login");
    }
    那么通过上面的配置，不用添加LoginController或者处理“login”的方法就可以直接通过
    “http://localhost:8080/projectContext/login”访问到login.html页面了！

	 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override//对静态资源进行处理
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        /**
         * 内置的VersionResourceResolver有FixedVersionStrategy和ContentVersionStrategy两种策略,
         * FixedVersionStrategy可以使用某项属性,或者日期,或者其它来作为版本.
         * 而ContentVersionStrategy是使用资源内容计算出来的MD5哈希作为版本.
         * ContentVersionStrategy是个不错的默认选择,除了某些不能使用的情况下
         * (比如,带有javascript模块加截器).
         * 以下配置:如果是js的话使用FixedVersionStrategy,其它(如css,img)使用默
         * 认的ContentVersionStrategy策略.
         */
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/").resourceChain(true).addResolver(
                new VersionResourceResolver().addFixedVersionStrategy("1.10", "/**/*.js").addContentVersionStrategy("/**"));


    }

}
