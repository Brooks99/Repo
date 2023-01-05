/*
 * Copyright (c) 2012 NoMagic, Inc. All Rights Reserved.
 */
package action;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.ModelElementsManager;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.task.BackgroundTaskRunner;
import com.nomagic.magicdraw.ui.MagicDrawProgressStatusRunner;
import com.nomagic.task.ProgressStatus;
import com.nomagic.task.RunnableWithProgress;
import com.nomagic.ui.ProgressStatusRunner;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.impl.ElementsFactory;

/**
 * Task with progress status execution sample.
 *
 * @author Martynas Lelevicius
 */
@SuppressWarnings({"squid:S106", "squid:S1148", "UnusedDeclaration"})
public class ProgressStatusSample
{
	private static int gmaxSteps;
	private static ElementsFactory gfactory;

	private static Package gparentPackage;

	private static ModelElementsManager gmanager;

	/**
	 * MagicDraw UI is not locked during execution - single change affects UI.
	 *
	 * @param project project.
	 * @param maxSteps
	 */
	public static void nonLockedExecution(final Project project,
										  int maxSteps,
										  ElementsFactory factory,
										  Package parentPackage,
										 ModelElementsManager manager)
	{
		gfactory = factory;
		gmaxSteps = maxSteps;
		gparentPackage = parentPackage;
		gmanager = manager;
		RunnableWithProgress r = createRunnable(project);
		ProgressStatusRunner.runWithProgressStatus(r, "Part Instances", true, 0);
	}

	/**
	 * MagicDraw UI is locked during execution - UI is updated only when task finishes (single changes does not affect UI).
	 *
	 * @param project project.
	 */
	public static void lockedExecution(final Project project)
	{
		MagicDrawProgressStatusRunner.runWithProgressStatus(createRunnable(project),
				"Locked Progress Test", true, 0);
	}

	/**
	 * Execute task in background.
	 *
	 * @param allowCancel true if cancel should be enabled.
	 */
	public static void backgroundExecution(boolean allowCancel)
	{
		BackgroundTaskRunner.runWithProgressStatus(progressStatus -> {
			final int max = 200;
			progressStatus.init("My Progress...", 0, max);
			for (int i = 0; i < max && !progressStatus.isCancel(); ++i)
			{
				progressStatus.increase();
				sleep(100);
			}
		}, "My Task", allowCancel);
	}

	private static void sleep(int millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Create task runnable.
	 *
	 * @param project project.
	 * @return runnable.
	 */
	private static SampleRunnableWithProgress createRunnable(final Project project)
	{
		return new SampleRunnableWithProgress(project, 3000, gfactory, gparentPackage, gmanager);
	}


}
