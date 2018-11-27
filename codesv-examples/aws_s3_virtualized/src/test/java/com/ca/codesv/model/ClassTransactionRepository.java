/******************************************************************************
 *
 * Copyright (c) 2018 CA.  All rights reserved.
 *
 * This software and all information contained therein is confidential and
 * proprietary and shall not be duplicated, used, disclosed or disseminated
 * in any way except as authorized by the applicable license agreement,
 * without the express written permission of CA. All authorized reproductions
 * must be marked with this language.
 *
 * EXCEPT AS SET FORTH IN THE APPLICABLE LICENSE AGREEMENT, TO THE EXTENT
 * PERMITTED BY APPLICABLE LAW, CA PROVIDES THIS SOFTWARE WITHOUT
 * WARRANTY OF ANY KIND, INCLUDING WITHOUT LIMITATION, ANY IMPLIED
 * WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  IN
 * NO EVENT WILL CA BE LIABLE TO THE END USER OR ANY THIRD PARTY FOR ANY
 * LOSS OR DAMAGE, DIRECT OR INDIRECT, FROM THE USE OF THIS SOFTWARE,
 * INCLUDING WITHOUT LIMITATION, LOST PROFITS, BUSINESS INTERRUPTION,
 * GOODWILL, OR LOST DATA, EVEN IF CA IS EXPRESSLY ADVISED OF SUCH LOSS OR
 * DAMAGE.
 *
 ******************************************************************************/

package com.ca.codesv.model;

import com.ca.codesv.sdk.annotation.TransactionDefinition;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Defines common functionality for Class transaction repositories.
 *
 * Due to the static nature of class repositories, this is rather "utility" class than real base class of class repositories.
 */
public abstract class ClassTransactionRepository {

    private static final String NAME_FIELD = "name";
    private static final String TAGS_FIELD = "tags";

    /**
     * Returns names of defined transactions for a given Class transaction repository.
     *
     * In case no transactions are defined, returns an empty list.
     *
     * @param repository transaction repository class
     * @return list of transaction names defined in repository
     */
    public static final List<String> getTransactionNames(Class<? extends ClassTransactionRepository> repository) {
        List<Annotation> annotations = getDeclaredTransactionAnnotations(repository);

        List<String> transactionNames = annotations.stream()
                .map(annotation -> getAnnotationParameterValue(annotation, String.class, NAME_FIELD))
                .collect(Collectors.toList());

        return transactionNames;
    }

    /**
     * Returns names of defined transactions for a given Class transaction repository and tag.
     *
     * In case no transactions are defined, returns an empty list.
     *
     * @param repository transaction repository class
     * @param tag tag for which to find transactions
     * @return list of transaction names defined in repository
     */
    public static final List<String> getTransactionNamesByTag(Class<? extends ClassTransactionRepository> repository, String tag) {
        List<Annotation> annotations = getDeclaredTransactionAnnotations(repository);

        List<String> transactionNames = annotations.stream()
                .filter(annotation -> Arrays.asList(getAnnotationParameterValue(annotation, String[].class, TAGS_FIELD)).contains(tag))
                .map(annotation -> getAnnotationParameterValue(annotation, String.class, NAME_FIELD)).collect(Collectors.toList());

        return transactionNames;
    }

    private static List<Annotation> getDeclaredTransactionAnnotations(Class<? extends ClassTransactionRepository> repository) {
        List<Annotation> annotations = new ArrayList<Annotation>();

        Method[] methods = repository.getDeclaredMethods();

        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(TransactionDefinition.class);
            if (annotation != null) {
                annotations.add(annotation);
            }
        }

        return annotations;
    }

    private static <T> T getAnnotationParameterValue(Annotation annotation, Class<T> type, String parameterName) {
        T result = null;
        try {
            result = type.cast(annotation.annotationType().getMethod(parameterName).invoke(annotation));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result;
    }

}
