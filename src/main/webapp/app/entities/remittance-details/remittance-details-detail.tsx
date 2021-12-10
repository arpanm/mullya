import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './remittance-details.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RemittanceDetailsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const remittanceDetailsEntity = useAppSelector(state => state.remittanceDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="remittanceDetailsDetailsHeading">
          <Translate contentKey="mulyaaApp.remittanceDetails.detail.title">RemittanceDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{remittanceDetailsEntity.id}</dd>
          <dt>
            <span id="paymentType">
              <Translate contentKey="mulyaaApp.remittanceDetails.paymentType">Payment Type</Translate>
            </span>
          </dt>
          <dd>{remittanceDetailsEntity.paymentType}</dd>
          <dt>
            <span id="onlinePgType">
              <Translate contentKey="mulyaaApp.remittanceDetails.onlinePgType">Online Pg Type</Translate>
            </span>
          </dt>
          <dd>{remittanceDetailsEntity.onlinePgType}</dd>
          <dt>
            <span id="pgTxnId">
              <Translate contentKey="mulyaaApp.remittanceDetails.pgTxnId">Pg Txn Id</Translate>
            </span>
          </dt>
          <dd>{remittanceDetailsEntity.pgTxnId}</dd>
          <dt>
            <span id="pgStatus">
              <Translate contentKey="mulyaaApp.remittanceDetails.pgStatus">Pg Status</Translate>
            </span>
          </dt>
          <dd>{remittanceDetailsEntity.pgStatus}</dd>
          <dt>
            <span id="offlineTxnId">
              <Translate contentKey="mulyaaApp.remittanceDetails.offlineTxnId">Offline Txn Id</Translate>
            </span>
          </dt>
          <dd>{remittanceDetailsEntity.offlineTxnId}</dd>
          <dt>
            <span id="offlineTxnDetails">
              <Translate contentKey="mulyaaApp.remittanceDetails.offlineTxnDetails">Offline Txn Details</Translate>
            </span>
          </dt>
          <dd>{remittanceDetailsEntity.offlineTxnDetails}</dd>
          <dt>
            <span id="offlineTxnGivenBy">
              <Translate contentKey="mulyaaApp.remittanceDetails.offlineTxnGivenBy">Offline Txn Given By</Translate>
            </span>
          </dt>
          <dd>{remittanceDetailsEntity.offlineTxnGivenBy}</dd>
          <dt>
            <span id="offlineTxnClearingStatus">
              <Translate contentKey="mulyaaApp.remittanceDetails.offlineTxnClearingStatus">Offline Txn Clearing Status</Translate>
            </span>
          </dt>
          <dd>{remittanceDetailsEntity.offlineTxnClearingStatus}</dd>
          <dt>
            <span id="remittanceDate">
              <Translate contentKey="mulyaaApp.remittanceDetails.remittanceDate">Remittance Date</Translate>
            </span>
          </dt>
          <dd>{remittanceDetailsEntity.remittanceDate}</dd>
          <dt>
            <span id="remittanceInitTime">
              <Translate contentKey="mulyaaApp.remittanceDetails.remittanceInitTime">Remittance Init Time</Translate>
            </span>
          </dt>
          <dd>
            {remittanceDetailsEntity.remittanceInitTime ? (
              <TextFormat value={remittanceDetailsEntity.remittanceInitTime} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="remittanceUpdateTime">
              <Translate contentKey="mulyaaApp.remittanceDetails.remittanceUpdateTime">Remittance Update Time</Translate>
            </span>
          </dt>
          <dd>
            {remittanceDetailsEntity.remittanceUpdateTime ? (
              <TextFormat value={remittanceDetailsEntity.remittanceUpdateTime} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="paymentStatus">
              <Translate contentKey="mulyaaApp.remittanceDetails.paymentStatus">Payment Status</Translate>
            </span>
          </dt>
          <dd>{remittanceDetailsEntity.paymentStatus}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mulyaaApp.remittanceDetails.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {remittanceDetailsEntity.createdOn ? (
              <TextFormat value={remittanceDetailsEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mulyaaApp.remittanceDetails.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{remittanceDetailsEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mulyaaApp.remittanceDetails.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {remittanceDetailsEntity.updatedOn ? (
              <TextFormat value={remittanceDetailsEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mulyaaApp.remittanceDetails.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{remittanceDetailsEntity.updatedBy}</dd>
          <dt>
            <Translate contentKey="mulyaaApp.remittanceDetails.farmer">Farmer</Translate>
          </dt>
          <dd>{remittanceDetailsEntity.farmer ? remittanceDetailsEntity.farmer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/remittance-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/remittance-details/${remittanceDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RemittanceDetailsDetail;
