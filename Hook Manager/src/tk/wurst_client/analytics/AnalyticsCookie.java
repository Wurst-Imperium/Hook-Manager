/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.analytics;

import java.security.SecureRandom;

public class AnalyticsCookie
{
	public boolean enabled = true;
	public int id = new SecureRandom().nextInt() & 0x7FFFFFFF;
	public long first_launch = System.currentTimeMillis() / 1000L;
	public long last_launch = System.currentTimeMillis() / 1000L;
	public int launches = 0;
}
