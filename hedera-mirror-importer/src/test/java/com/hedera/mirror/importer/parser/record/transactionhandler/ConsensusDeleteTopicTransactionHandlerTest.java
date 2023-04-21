/*
 * Copyright (C) 2019-2023 Hedera Hashgraph, LLC
 *
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
 */

package com.hedera.mirror.importer.parser.record.transactionhandler;

import com.hedera.mirror.common.domain.entity.EntityType;
import com.hederahashgraph.api.proto.java.ConsensusDeleteTopicTransactionBody;
import com.hederahashgraph.api.proto.java.TopicID;
import com.hederahashgraph.api.proto.java.TransactionBody;

class ConsensusDeleteTopicTransactionHandlerTest extends AbstractDeleteOrUndeleteTransactionHandlerTest {

    @Override
    protected TransactionHandler getTransactionHandler() {
        return new ConsensusDeleteTopicTransactionHandler(entityIdService, entityListener);
    }

    @Override
    protected TransactionBody.Builder getDefaultTransactionBody() {
        return TransactionBody.newBuilder()
                .setConsensusDeleteTopic(ConsensusDeleteTopicTransactionBody.newBuilder()
                        .setTopicID(TopicID.newBuilder()
                                .setTopicNum(DEFAULT_ENTITY_NUM)
                                .build()));
    }

    @Override
    protected EntityType getExpectedEntityIdType() {
        return EntityType.TOPIC;
    }
}
