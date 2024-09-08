package com.sushistack.jpashop.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.declaredMemberProperties


@Target(PROPERTY)
annotation class Log

@Component
class LoggingInjector : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String) =
        bean.also {
            try {
                val loggerName = it::class.java.canonicalName!!
                processObject(it, loggerName)
                it::class.companionObjectInstance?.let { companion ->
                    processObject(companion, loggerName)
                }
            } catch (ignored: Throwable) {/* ignore */}
        }

    private fun processObject(target: Any, loggerName: String) {
        target::class.declaredMemberProperties.forEach { property ->
            if (property is KMutableProperty<*>) {
                repeat(property.annotations.filterIsInstance<Log>().size) {
                    property.setter.call(target, LoggerFactory.getLogger(loggerName))
                }
            }
        }
    }
}