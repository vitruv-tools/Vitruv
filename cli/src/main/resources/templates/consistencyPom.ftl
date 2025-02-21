
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
        <parent>
            <groupId>tools.vitruv</groupId>
            <artifactId>${packageName}</artifactId>
            <version>0.1.0-SNAPSHOT</version>
        </parent>
        <artifactId>${packageName}.consistency</artifactId>
        <#noparse>
        <name>Consistency</name>
        <description />

        <dependencies>
        <!-- project dependencies-->
        <dependency>
        <groupId>${project.groupId}</groupId>
    </#noparse>
    <artifactId>${packageName}.model</artifactId>
    <#noparse>
        <version>${project.version}</version>
        </dependency>

        <!-- Vitruvius dependencies -->
        <dependency>
            <groupId>tools.vitruv</groupId>
            <artifactId>tools.vitruv.change.composite</artifactId>
        </dependency>
        <dependency>
            <groupId>tools.vitruv</groupId>
            <artifactId>tools.vitruv.change.propagation</artifactId>
        </dependency>
        <dependency>
            <groupId>tools.vitruv</groupId>
            <artifactId>tools.vitruv.dsls.reactions.runtime</artifactId>
        </dependency>

        <!-- external dependencies -->
        <dependency>
            <groupId>org.eclipse.emf</groupId>
            <artifactId>org.eclipse.emf.ecore</artifactId>
        </dependency>
        </dependencies>

        <build>
        <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>build-helper-maven-plugin</artifactId>
            <executions>
                <execution>
                    <id>add-source-reactions</id>
                    <phase>generate-sources</phase>
                    <goals>
                        <goal>add-source</goal>
                    </goals>
                    <configuration>
                        <sources>
                            <source>${project.basedir}/src/main/reactions</source>
                            <source>${project.build.directory}/generated-sources/reactions</source>
                        </sources>
                    </configuration>
                </execution>
            </executions>
        </plugin>
        <plugin>
        <groupId>org.eclipse.xtext</groupId>
        <artifactId>xtext-maven-plugin</artifactId>
        <configuration>
            <languages>
                <language>
                    <setup>tools.vitruv.dsls.reactions.ReactionsLanguageStandaloneSetup</setup>
                    <outputConfigurations>
                        <outputConfiguration>
                            <outputDirectory>
                                ${project.build.directory}/generated-sources/reactions</outputDirectory>
                        </outputConfiguration>
                    </outputConfigurations>
                </language>
                <language>
                    <setup>org.eclipse.xtend.core.XtendStandaloneSetup</setup>
                    <outputConfigurations>
                        <outputConfiguration>
                            <outputDirectory>
                                ${project.build.directory}/generated-sources/xtend</outputDirectory>
                        </outputConfiguration>
                    </outputConfigurations>
                </language>
            </languages>
        </configuration>
        <dependencies>
        <dependency>
            <groupId>tools.vitruv</groupId>
            <artifactId>tools.vitruv.dsls.reactions.language</artifactId>
            <version>3.1.0</version>
        </dependency>
        <dependency>
            <groupId>tools.vitruv</groupId>
            <artifactId>tools.vitruv.dsls.reactions.runtime</artifactId>
            <version>3.1.0</version>
        </dependency>
        <dependency>
            <groupId>tools.vitruv</groupId>
            <artifactId>tools.vitruv.dsls.common</artifactId>
            <version>3.1.0</version>
        </dependency>
        <dependency>
        <groupId>${project.groupId}</groupId>
    </#noparse>
    <artifactId>${packageName}.model</artifactId>
    <#noparse>
    <version>${project.version}</version>
    </dependency>
    </dependencies>
    </plugin>
    </plugins>
    </build>
</project>
</#noparse>