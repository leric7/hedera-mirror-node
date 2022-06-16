/*-
 * ‌
 * Hedera Mirror Node
 * ​
 * Copyright (C) 2019 - 2022 Hedera Hashgraph, LLC
 * ​
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ‍
 */

'use strict';

class NodeStake {
  /**
   * Parses node_stake table columns into object
   */
  constructor(nodeStake) {
    this.consensusTimestamp = nodeStake.consensus_timestamp;
    this.epochDay = nodeStake.epoch_day;
    this.nodeId = nodeStake.node_id;
    this.rewardRate = nodeStake.reward_rate;
    this.stake = nodeStake.stake;
    this.stakeRewarded = nodeStake.stake_rewarded;
    this.stakeTotal = nodeStake.stake_total;
    this.stakingPeriod = nodeStake.staking_period;
  }

  static tableAlias = 'ns';
  static tableName = 'node_stake';

  static CONSENSUS_TIMESTAMP = `consensus_timestamp`;
  static EPOCH_DAY = `epoch_day`;
  static NODE_ID = `node_id`;
  static REWARD_RATE = `reward_rate`;
  static STAKE = `stake`;
  static STAKE_REWARDED = `stake_rewarded`;
  static STAKE_TOTAL = `stake_total`;
  static STAKING_PERIOD = `staking_period`;

  /**
   * Gets full column name with table alias prepended.
   *
   * @param {string} columnName
   * @private
   */
  static getFullName(columnName) {
    return `${this.tableAlias}.${columnName}`;
  }
}

module.exports = NodeStake;
